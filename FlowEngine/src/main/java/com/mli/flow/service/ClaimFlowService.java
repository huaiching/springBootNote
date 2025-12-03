package com.mli.flow.service;

import com.mli.flow.constants.ClaimStatusEnum;
import com.mli.flow.entity.ClaimHistoryEntity;
import com.mli.flow.entity.ClaimStatusEntity;
import com.mli.flow.exceptions.BusinessException;
import com.mli.flow.repository.ClaimHistoryRepository;
import com.mli.flow.repository.ClaimStatusRepository;
import com.mli.flow.util.DateUtil;
import com.mli.flow.vo.ClaimHistoryVo;
import com.mli.flow.vo.ClaimStatusVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 理賠 流程引擎
 */
@Service
public class ClaimFlowService {
    @Autowired
    private ClaimStatusRepository claimStatusRepository;
    @Autowired
    private ClaimHistoryRepository claimHistoryRepository;
    @Autowired
    private ClaimSpELService claimSpELService;

    /**
     * 理賠流程 - 新增案件
     * @param clientId 申請人ID
     * @param claimSeq 建檔序號
     * @return ClaimStatusEntity 新增案件資訊
     */
    @Transactional
    public ClaimStatusVo createClaimStatus(String clientId, Integer claimSeq) {
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        if (StringUtils.isEmpty(claimSeq)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }

        // 取得目前案件資訊
        ClaimStatusVo claimStatusVo = getClaimStatus(clientId, claimSeq);
        if (claimStatusVo != null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件執行錯誤，案件已存在，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        // 取得 下一關節點
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", "");    // 目前節點 = 空白

        String nextStatus = claimSpELService.getNextStatus(dataMap);
        if (StringUtils.isEmpty(nextStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件錯誤，找不到案件狀態，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        // 建立資料
        ClaimStatusEntity newClaimStatus = new ClaimStatusEntity();
        newClaimStatus.setUuid(UUID.randomUUID().toString());
        newClaimStatus.setClientId(clientId);
        newClaimStatus.setClaimSeq(claimSeq);
        newClaimStatus.setStatus(nextStatus);
        newClaimStatus.setProcessUser("測試人員");
        newClaimStatus.setProcessDate(DateUtil.getToday());
        newClaimStatus.setProcessTime(DateUtil.getTime());

        // 新增案件狀態
        claimStatusRepository.save(newClaimStatus);

        // 新增案件歷程
        ClaimHistoryEntity newClaimHistory = new ClaimHistoryEntity();
        BeanUtils.copyProperties(newClaimStatus, newClaimHistory);
        claimHistoryRepository.save(newClaimHistory);

        // 設定 回傳資料
        ClaimStatusVo output = new ClaimStatusVo();
        BeanUtils.copyProperties(newClaimStatus, output);
        output.setStatusDesc(ClaimStatusEnum.getDescByStatusCode(output.getStatus()));

        return output;
    }

    /**
     * 理賠流程 - 前往下一關
     * @param clientId 申請人ID
     * @param claimSeq 建檔序號
     * @return ClaimStatusEntity 下一關案件資訊
     */
    @Transactional
    public ClaimStatusVo nextClaimStatus(String clientId, Integer claimSeq) {
        if (StringUtils.isEmpty(clientId)) {
            throw new RuntimeException("輸入參數錯誤：clientId 空白");
        }
        if (StringUtils.isEmpty(claimSeq)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }

        // 取得目前案件資訊
        ClaimStatusVo claimStatusVo = getClaimStatus(clientId, claimSeq);

        if (claimStatusVo == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "前往下一關 執行錯誤，案件不存在，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        if ("4".equals(claimStatusVo.getStatus())) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "案件已結案，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        // 取得 下一關節點
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", claimStatusVo.getStatus());          // 目前節點 = 空白
        dataMap.put("subFlow", false);      // 不前往 子流程

        String nextStatus = claimSpELService.getNextStatus(dataMap);

        if (StringUtils.isEmpty(nextStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "前往下一關 執行錯誤，找不到下一關狀態，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        // 建立資料: 刪除
        ClaimStatusEntity oriClaimStatus = new ClaimStatusEntity();
        BeanUtils.copyProperties(claimStatusVo, oriClaimStatus);

        // 建立資料: 新增
        ClaimStatusEntity newClaimStatus = new ClaimStatusEntity();
        newClaimStatus.setUuid(UUID.randomUUID().toString());
        newClaimStatus.setClientId(claimStatusVo.getClientId());
        newClaimStatus.setClaimSeq(claimStatusVo.getClaimSeq());
        newClaimStatus.setStatus(nextStatus);
        newClaimStatus.setProcessUser(claimStatusVo.getProcessUser());
        newClaimStatus.setProcessDate(DateUtil.getToday());
        newClaimStatus.setProcessTime(DateUtil.getTime());

        // 修改案件狀態: 先刪除，再新增
        claimStatusRepository.delete(oriClaimStatus);
        claimStatusRepository.save(newClaimStatus);

        // 新增案件歷程
        ClaimHistoryEntity newClaimHistory = new ClaimHistoryEntity();
        BeanUtils.copyProperties(newClaimStatus, newClaimHistory);
        claimHistoryRepository.save(newClaimHistory);

        // 設定 回傳資料
        ClaimStatusVo output = new ClaimStatusVo();
        BeanUtils.copyProperties(newClaimStatus, output);
        output.setStatusDesc(ClaimStatusEnum.getDescByStatusCode(output.getStatus()));

        return output;
    }

    /**
     * 理賠流程 - 返回上一關
     * @param clientId 申請人ID
     * @param claimSeq 建檔序號
     * @return ClaimStatusVo 上一關案件資訊
     */
    @Transactional
    public ClaimStatusVo prewClaimStatus(String clientId, Integer claimSeq) {
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        if (StringUtils.isEmpty(claimSeq)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }

        // 取得目前案件資訊
        ClaimStatusVo claimStatusVo = getClaimStatus(clientId, claimSeq);

        if (claimStatusVo == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "返回上一關 執行錯誤，案件不存在，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        // 取得 下一關節點
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", claimStatusVo.getStatus());          // 目前節點 = 空白
        dataMap.put("subFlow", false);      // 不前往 子流程

        String prewStatus = claimSpELService.getPrewStatus(dataMap);

        if (StringUtils.isEmpty(prewStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "返回上一關 執行錯誤，找不到上一關狀態，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        // 建立資料: 刪除
        ClaimStatusEntity oriClaimStatus = new ClaimStatusEntity();
        BeanUtils.copyProperties(claimStatusVo, oriClaimStatus);

        // 建立資料: 新增
        ClaimStatusEntity newClaimStatus = new ClaimStatusEntity();
        newClaimStatus.setUuid(UUID.randomUUID().toString());
        newClaimStatus.setClientId(claimStatusVo.getClientId());
        newClaimStatus.setClaimSeq(claimStatusVo.getClaimSeq());
        newClaimStatus.setStatus(prewStatus);
        newClaimStatus.setProcessUser(claimStatusVo.getProcessUser());
        newClaimStatus.setProcessDate(DateUtil.getToday());
        newClaimStatus.setProcessTime(DateUtil.getTime());

        // 修改案件狀態: 先刪除，再新增
        claimStatusRepository.delete(oriClaimStatus);
        claimStatusRepository.save(newClaimStatus);

        // 新增案件歷程
        ClaimHistoryEntity newClaimHistory = new ClaimHistoryEntity();
        BeanUtils.copyProperties(newClaimStatus, newClaimHistory);
        claimHistoryRepository.save(newClaimHistory);

        // 設定 回傳資料
        ClaimStatusVo output = new ClaimStatusVo();
        BeanUtils.copyProperties(newClaimStatus, output);
        output.setStatusDesc(ClaimStatusEnum.getDescByStatusCode(output.getStatus()));

        return output;
    }

    /**
     * 理賠流程 - 送至照會
     * @param clientId 申請人ID
     * @param claimSeq 建檔序號
     * @return ClaimStatusEntity 下一關案件資訊
     */
    @Transactional
    public ClaimStatusVo subClaimStatus(String clientId, Integer claimSeq) {
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        if (StringUtils.isEmpty(claimSeq)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }

        // 取得目前案件資訊
        ClaimStatusVo claimStatusVo = getClaimStatus(clientId, claimSeq);

        if (claimStatusVo == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "送至照會 執行錯誤，案件不存在，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        if (!"2".equals(claimStatusVo.getStatus())) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "非審核 不可照會，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        // 取得 下一關節點
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", claimStatusVo.getStatus());          // 目前節點 = 空白
        dataMap.put("subFlow", true);      // 不前往 子流程

        String nextStatus = claimSpELService.getNextStatus(dataMap);

        if (StringUtils.isEmpty(nextStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "送至照會 執行錯誤，找不到下一關狀態，clientId=" + clientId + " claimSeq=" + claimSeq);
        }

        // 建立資料: 刪除
        ClaimStatusEntity oriClaimStatus = new ClaimStatusEntity();
        BeanUtils.copyProperties(claimStatusVo, oriClaimStatus);

        // 建立資料: 新增
        ClaimStatusEntity newClaimStatus = new ClaimStatusEntity();
        newClaimStatus.setUuid(UUID.randomUUID().toString());
        newClaimStatus.setClientId(claimStatusVo.getClientId());
        newClaimStatus.setClaimSeq(claimStatusVo.getClaimSeq());
        newClaimStatus.setStatus(nextStatus);
        newClaimStatus.setProcessUser(claimStatusVo.getProcessUser());
        newClaimStatus.setProcessDate(DateUtil.getToday());
        newClaimStatus.setProcessTime(DateUtil.getTime());

        // 修改案件狀態: 先刪除，再新增
        claimStatusRepository.delete(oriClaimStatus);
        claimStatusRepository.save(newClaimStatus);

        // 新增案件歷程
        ClaimHistoryEntity newClaimHistory = new ClaimHistoryEntity();
        BeanUtils.copyProperties(newClaimStatus, newClaimHistory);
        claimHistoryRepository.save(newClaimHistory);

        // 設定 回傳資料
        ClaimStatusVo output = new ClaimStatusVo();
        BeanUtils.copyProperties(newClaimStatus, output);
        output.setStatusDesc(ClaimStatusEnum.getDescByStatusCode(output.getStatus()));

        return output;
    }

    /**
     * 取得 目前案件資訊
     * @param clientId 申請人ID
     * @param claimSeq 建檔序號
     * @return ClaimStatusVo 目前案件資訊
     */
    public ClaimStatusVo getClaimStatus(String clientId, Integer claimSeq) {
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        if (StringUtils.isEmpty(claimSeq)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }

        // 取得 目前案件資訊
        List<ClaimStatusEntity> claimStatusEntityList =  claimStatusRepository.findByClientIdAndClaimSeq(clientId, claimSeq);
        if (CollectionUtils.isEmpty(claimStatusEntityList)) {
            return null;
        } else {
            // 設定回傳
            ClaimStatusVo claimStatusVo = new ClaimStatusVo();
            BeanUtils.copyProperties(claimStatusEntityList.get(0), claimStatusVo);
            claimStatusVo.setStatusDesc(ClaimStatusEnum.getDescByStatusCode(claimStatusVo.getStatus()));

            return claimStatusVo;
        }
    }

    /**
     * 取得 案件歷史資訊
     * @param clientId 申請人ID
     * @param claimSeq 建檔序號
     * @return List<ClaimHistoryVo> 案件歷史資訊
     */
    public List<ClaimHistoryVo> getClaimHistory(String clientId, Integer claimSeq) {
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        if (StringUtils.isEmpty(claimSeq)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }

        // 取得 案件歷史資訊
        List<ClaimHistoryEntity> claimHistoryEntityList =  claimHistoryRepository.findByClientIdAndClaimSeq(clientId, claimSeq);
        // 排序
        Comparator<ClaimHistoryEntity> comparator1 = Comparator.comparing(ClaimHistoryEntity::getProcessDate);
        Comparator<ClaimHistoryEntity> comparator2 = Comparator.comparing(ClaimHistoryEntity::getProcessTime);
        claimHistoryEntityList.sort(comparator1.thenComparing(comparator2));
        // 設定回傳
        List<ClaimHistoryVo> claimHistoryVoList = new ArrayList<>();
        for (ClaimHistoryEntity claimHistoryEntity : claimHistoryEntityList) {
            ClaimHistoryVo claimHistoryVo = new ClaimHistoryVo();
            BeanUtils.copyProperties(claimHistoryEntity, claimHistoryVo);
            claimHistoryVo.setStatusDesc(ClaimStatusEnum.getDescByStatusCode(claimHistoryVo.getStatus()));

            claimHistoryVoList.add(claimHistoryVo);
        }

        return claimHistoryVoList;
    }
}
