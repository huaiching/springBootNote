package com.mli.flow.service;

import com.mli.flow.constants.FlowTypeEnum;
import com.mli.flow.dto.SubFlowChangeDTO;
import com.mli.flow.dto.SubFlowCreateDTO;
import com.mli.flow.entity.ClaimHistoryEntity;
import com.mli.flow.entity.ClaimMainStatusEntity;
import com.mli.flow.entity.ClaimSubStatusEntity;
import com.mli.flow.entity.FlowDefinitionEntity;
import com.mli.flow.exceptions.BusinessException;
import com.mli.flow.service.crud.ClaimHistoryService;
import com.mli.flow.service.crud.ClaimMainStatusService;
import com.mli.flow.service.crud.ClaimSubStatusService;
import com.mli.flow.service.crud.FlowDefinitionService;
import com.mli.flow.util.MliDateUtil;
import com.mli.flow.vo.ClaimSubStatusVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 流程引擎 子流程 相關邏輯
 */

@Service
public class ClaimSubFlowService {
    @Autowired
    private SpelExpressionService spelExpressionService;
    @Autowired
    private ClaimMainStatusService claimMainStatusService;
    @Autowired
    private ClaimSubStatusService claimSubStatusService;
    @Autowired
    private ClaimHistoryService claimHistoryService;
    @Autowired
    private FlowDefinitionService flowDefinitionService;

    /**
     * 理賠流程 新增案件
     * @param subFlowCreateDTO 新增流程 Input
     */
    public ClaimSubStatusVO createFlow(SubFlowCreateDTO subFlowCreateDTO) {
        // 初始資料檢查
        String moduleType = subFlowCreateDTO.getModuleType();
        if (StringUtils.isEmpty(moduleType)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：moduleType 空白");
        }
        String clientId = subFlowCreateDTO.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        Integer clfpSeq = subFlowCreateDTO.getClfpSeq();
        if (clfpSeq == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clfpSeq 空白");
        }
        String ownerUser = subFlowCreateDTO.getOwnerUser();
        if (StringUtils.isEmpty(ownerUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：ownerUser 空白");
        }
        String processUser = subFlowCreateDTO.getProcessUser();
        if (StringUtils.isEmpty(processUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：processUser 空白");
        }

        // 取得 主流程
        ClaimMainStatusEntity mainStatusEntity = claimMainStatusService.getCurrent(clientId, clfpSeq);
        if (mainStatusEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件 執行錯誤: 主流程不存在！");
        }

        // 取得 當下流程
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", "");

        FlowDefinitionEntity flowDefinitionEntity = spelExpressionService.getFlow(moduleType, dataMap);
        if (flowDefinitionEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件 執行錯誤: 找不到對應流程！");
        }
        String nextStatus = flowDefinitionEntity.getNextStatus();

        // 新增案件
        ClaimSubStatusEntity claimSubStatusEntity = new ClaimSubStatusEntity();
        claimSubStatusEntity.setCaseUuid(UUID.randomUUID().toString());
        claimSubStatusEntity.setMainUuid(mainStatusEntity.getMainUuid());
        claimSubStatusEntity.setSubUuid(UUID.randomUUID().toString());
        claimSubStatusEntity.setClientId(clientId);
        claimSubStatusEntity.setClfpSeq(clfpSeq);
        claimSubStatusEntity.setModuleType(moduleType);
        claimSubStatusEntity.setStatus(nextStatus);
        claimSubStatusEntity.setNote("");
        claimSubStatusEntity.setOwnerUser(ownerUser);
        claimSubStatusEntity.setProcessUser(processUser);
        claimSubStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimSubStatusEntity.setProcessTime(MliDateUtil.getTime());

        claimSubStatusService.insert(claimSubStatusEntity);

        // 新增 案件歷程表
        ClaimHistoryEntity claimHistoryEntity = new ClaimHistoryEntity();
        BeanUtils.copyProperties(claimSubStatusEntity, claimHistoryEntity);
        claimHistoryEntity.setMainStatus(mainStatusEntity.getStatus());
        claimHistoryEntity.setSubStatus(claimSubStatusEntity.getStatus());
        claimHistoryEntity.setFlowType(FlowTypeEnum.SUB.getCode());
        claimHistoryService.insert(claimHistoryEntity);

        // 取得 中文
        String status = nextStatus + " " + flowDefinitionService.getCliamStatusDesc(moduleType, nextStatus);

        // 設定回傳資料
        ClaimSubStatusVO claimSubStatusVO = new ClaimSubStatusVO();
        BeanUtils.copyProperties(claimSubStatusEntity, claimSubStatusVO);
        claimSubStatusVO.setStatus(status);

        return claimSubStatusVO;
    }

    /**
     * 理賠流程 下一關
     * @param subFlowChangeDTO 異動流程 Input
     */
    public ClaimSubStatusVO nextFlow(SubFlowChangeDTO subFlowChangeDTO) {
        // 初始資料檢查
        String subUuid = subFlowChangeDTO.getSubUuid();
        if (StringUtils.isEmpty(subUuid)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：subUuid 空白");
        }
        String ownerUser = subFlowChangeDTO.getOwnerUser();
        if (StringUtils.isEmpty(ownerUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：ownerUser 空白");
        }
        String processUser = subFlowChangeDTO.getProcessUser();
        if (StringUtils.isEmpty(processUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：processUser 空白");
        }

        // 取得目前案件
        ClaimSubStatusEntity current = claimSubStatusService.getCurrentBySubUuid(subUuid);
        if (current == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 案件不存在！");
        }

        String moduleType = current.getModuleType();

        // 取得 主流程
        ClaimMainStatusEntity mainStatusEntity = claimMainStatusService.getCurrentByMainUuid(current.getMainUuid());
        if (mainStatusEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件 執行錯誤: 主流程不存在！");
        }

        // 取得 當下流程
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", current.getStatus());

        FlowDefinitionEntity flowDefinitionEntity = spelExpressionService.getFlow(moduleType, dataMap);
        if (flowDefinitionEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 找不到對應流程！");
        }
        String nextStatus = flowDefinitionEntity.getNextStatus();

        if (StringUtils.isEmpty(nextStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 查無下一個流程！");
        }

        // 刪除 當下流程
        claimSubStatusService.delete(current);

        // 判斷 是否為 最後一個流程
        Map<String, Object> nextDataMap = new HashMap<>();
        nextDataMap.put("status", nextStatus);

        FlowDefinitionEntity nextFlowDefinitionEntity = spelExpressionService.getFlow(moduleType, nextDataMap);

        // 寫入新流程 : 最後一個流程 不寫入
        ClaimSubStatusEntity claimSubStatusEntity = new ClaimSubStatusEntity();
        claimSubStatusEntity.setCaseUuid(UUID.randomUUID().toString());
        claimSubStatusEntity.setMainUuid(current.getMainUuid());
        claimSubStatusEntity.setSubUuid(current.getSubUuid());
        claimSubStatusEntity.setClientId(current.getClientId());
        claimSubStatusEntity.setClfpSeq(current.getClfpSeq());
        claimSubStatusEntity.setModuleType(moduleType);
        claimSubStatusEntity.setStatus(nextStatus);
        claimSubStatusEntity.setNote(subFlowChangeDTO.getNote());
        claimSubStatusEntity.setOwnerUser(ownerUser);
        claimSubStatusEntity.setProcessUser(processUser);
        claimSubStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimSubStatusEntity.setProcessTime(MliDateUtil.getTime());

        if (!StringUtils.isEmpty(nextFlowDefinitionEntity.getNextStatus())) {
            claimSubStatusService.insert(claimSubStatusEntity);
        }

        // 新增 案件歷程表
        ClaimHistoryEntity claimHistoryEntity = new ClaimHistoryEntity();
        BeanUtils.copyProperties(claimSubStatusEntity, claimHistoryEntity);
        claimHistoryEntity.setMainStatus(mainStatusEntity.getStatus());
        claimHistoryEntity.setSubStatus(claimSubStatusEntity.getStatus());
        claimHistoryEntity.setFlowType(FlowTypeEnum.SUB.getCode());
        claimHistoryService.insert(claimHistoryEntity);

        // 取得 中文
        String status = nextStatus + " " + flowDefinitionService.getCliamStatusDesc(moduleType, nextStatus);

        // 設定回傳資料
        ClaimSubStatusVO claimSubStatusVO = new ClaimSubStatusVO();
        BeanUtils.copyProperties(claimSubStatusEntity, claimSubStatusVO);
        claimSubStatusVO.setStatus(status);

        return claimSubStatusVO;
    }


    /**
     * 理賠流程 上一關
     * @param subFlowChangeDTO 異動流程 Input
     */
    public ClaimSubStatusVO previousFlow(SubFlowChangeDTO subFlowChangeDTO) {
        // 初始資料檢查
        String subUuid = subFlowChangeDTO.getSubUuid();
        if (StringUtils.isEmpty(subUuid)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：subUuid 空白");
        }
        String ownerUser = subFlowChangeDTO.getOwnerUser();
        if (StringUtils.isEmpty(ownerUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：ownerUser 空白");
        }
        String processUser = subFlowChangeDTO.getProcessUser();
        if (StringUtils.isEmpty(processUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：processUser 空白");
        }

        // 取得目前案件
        ClaimSubStatusEntity current = claimSubStatusService.getCurrentBySubUuid(subUuid);
        if (current == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 案件不存在！");
        }

        String moduleType = current.getModuleType();

        // 取得 主流程
        ClaimMainStatusEntity mainStatusEntity = claimMainStatusService.getCurrentByMainUuid(current.getMainUuid());
        if (mainStatusEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件 執行錯誤: 主流程不存在！");
        }

        // 取得 當下流程
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", current.getStatus());

        FlowDefinitionEntity flowDefinitionEntity = spelExpressionService.getFlow(moduleType, dataMap);
        if (flowDefinitionEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 找不到對應流程！");
        }
        String prewStatus = flowDefinitionEntity.getPrewStatus();

        if (StringUtils.isEmpty(prewStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 查無上一個流程！");
        }

        // 刪除 當下流程
        claimSubStatusService.delete(current);

        // 寫入新流程
        ClaimSubStatusEntity claimSubStatusEntity = new ClaimSubStatusEntity();
        claimSubStatusEntity.setCaseUuid(UUID.randomUUID().toString());
        claimSubStatusEntity.setMainUuid(current.getMainUuid());
        claimSubStatusEntity.setSubUuid(current.getSubUuid());
        claimSubStatusEntity.setClientId(current.getClientId());
        claimSubStatusEntity.setClfpSeq(current.getClfpSeq());
        claimSubStatusEntity.setModuleType(moduleType);
        claimSubStatusEntity.setStatus(prewStatus);
        claimSubStatusEntity.setNote(subFlowChangeDTO.getNote());
        claimSubStatusEntity.setOwnerUser(ownerUser);
        claimSubStatusEntity.setProcessUser(processUser);
        claimSubStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimSubStatusEntity.setProcessTime(MliDateUtil.getTime());

        claimSubStatusService.insert(claimSubStatusEntity);

        // 新增 案件歷程表
        ClaimHistoryEntity claimHistoryEntity = new ClaimHistoryEntity();
        BeanUtils.copyProperties(claimSubStatusEntity, claimHistoryEntity);
        claimHistoryEntity.setMainStatus(mainStatusEntity.getStatus());
        claimHistoryEntity.setSubStatus(claimSubStatusEntity.getStatus());
        claimHistoryEntity.setFlowType(FlowTypeEnum.SUB.getCode());
        claimHistoryService.insert(claimHistoryEntity);

        // 取得 中文
        String status = prewStatus + " " + flowDefinitionService.getCliamStatusDesc(moduleType, prewStatus);

        // 設定回傳資料
        ClaimSubStatusVO claimSubStatusVO = new ClaimSubStatusVO();
        BeanUtils.copyProperties(claimSubStatusEntity, claimSubStatusVO);
        claimSubStatusVO.setStatus(status);

        return claimSubStatusVO;
    }
}
