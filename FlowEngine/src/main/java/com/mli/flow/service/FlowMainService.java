package com.mli.flow.service;

import com.mli.flow.constants.ValidEnum;
import com.mli.flow.dto.FlowChangeDTO;
import com.mli.flow.dto.FlowCreateDTO;
import com.mli.flow.entity.ClaimMainStatusEntity;
import com.mli.flow.entity.ClaimSubStatusEntity;
import com.mli.flow.entity.FlowDefinitionEntity;
import com.mli.flow.exceptions.BusinessException;
import com.mli.flow.service.crud.ClaimMainStatusService;
import com.mli.flow.service.crud.ClaimSubStatusService;
import com.mli.flow.service.crud.FlowDefinitionService;
import com.mli.flow.util.MliDateUtil;
import com.mli.flow.vo.ClaimMainStatusVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 流程引擎 主流程 相關邏輯
 */

@Service
public class FlowMainService {
    @Autowired
    private SpelExpressionService spelExpressionService;
    @Autowired
    private ClaimMainStatusService claimMainStatusService;
    @Autowired
    private ClaimSubStatusService claimSubStatusService;
    @Autowired
    private FlowDefinitionService flowDefinitionService;

    private final String CLAIM_MODULE = "claim";

    /**
     * 理賠流程 新增案件
     * @param flowCreateDTO 新增流程 Input
     */
    public ClaimMainStatusVO createFlow(FlowCreateDTO flowCreateDTO) {
        // 初始資料檢查
        String clientId = flowCreateDTO.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        Integer claimSeq = flowCreateDTO.getClaimSeq();
        if (claimSeq == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }
        String ownerUser = flowCreateDTO.getOwnerUser();
        if (StringUtils.isEmpty(ownerUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：ownerUser 空白");
        }
        String processUser = flowCreateDTO.getProcessUser();
        if (StringUtils.isEmpty(processUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：processUser 空白");
        }

        // 檢查 案件是否已建立
        if (claimMainStatusService.getCurrentData(clientId, claimSeq) != null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件 執行錯誤: 案件已存在！");
        }

        // 取得 當下流程
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", "");

        FlowDefinitionEntity flowDefinitionEntity = spelExpressionService.getFlow(CLAIM_MODULE, dataMap);
        if (flowDefinitionEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件 執行錯誤: 找不到對應流程！");
        }
        String nextStatus = flowDefinitionEntity.getNextStatus();

        // 新增案件
        ClaimMainStatusEntity claimMainStatusEntity = new ClaimMainStatusEntity();
        claimMainStatusEntity.setCaseUuid(UUID.randomUUID().toString());
        claimMainStatusEntity.setMainUuid(UUID.randomUUID().toString());
        claimMainStatusEntity.setClientId(clientId);
        claimMainStatusEntity.setClaimSeq(claimSeq);
        claimMainStatusEntity.setModuleType(CLAIM_MODULE);
        claimMainStatusEntity.setMainStatus(nextStatus);
        claimMainStatusEntity.setValid(ValidEnum.VALID.getCode());
        claimMainStatusEntity.setNote("");
        claimMainStatusEntity.setOwnerUser(ownerUser);
        claimMainStatusEntity.setProcessUser(processUser);
        claimMainStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimMainStatusEntity.setProcessTime(MliDateUtil.getTime());

        claimMainStatusService.insert(claimMainStatusEntity);

        // 取得 中文
        String mainStatusDesc = flowDefinitionService.getCliamStatusDesc(CLAIM_MODULE, nextStatus);
        String validDesc = ValidEnum.getDescByCode(claimMainStatusEntity.getValid());

        // 設定回傳資料
        ClaimMainStatusVO claimMainStatusVO = new ClaimMainStatusVO();
        BeanUtils.copyProperties(claimMainStatusEntity, claimMainStatusVO);
        claimMainStatusVO.setMainStatus(nextStatus + " " + mainStatusDesc);
        claimMainStatusVO.setValid(claimMainStatusVO.getValid() + " " + validDesc);

        return claimMainStatusVO;
    }

    /**
     * 理賠流程 下一關
     * @param flowChangeDTO 異動流程 Input
     */
    public ClaimMainStatusVO nextFlow(FlowChangeDTO flowChangeDTO) {
        // 初始資料檢查
        String clientId = flowChangeDTO.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        Integer claimSeq = flowChangeDTO.getClaimSeq();
        if (claimSeq == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }
        String ownerUser = flowChangeDTO.getOwnerUser();
        if (StringUtils.isEmpty(ownerUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：ownerUser 空白");
        }
        String processUser = flowChangeDTO.getProcessUser();
        if (StringUtils.isEmpty(processUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：processUser 空白");
        }

        // 取得目前案件
        ClaimMainStatusEntity current = claimMainStatusService.getCurrentData(clientId, claimSeq);

        // 檢查 案件是否已建立
        if (current == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 案件不存在！");
        }

        // 檢查 是否有 未結案子流程
        List<ClaimSubStatusEntity> claimSubStatusEntityList = claimSubStatusService.getValidData(clientId, claimSeq);
        if (!CollectionUtils.isEmpty(claimSubStatusEntityList)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 尚有子流程未結案！");
        }

        // 取得 當下流程
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", current.getMainStatus());

        FlowDefinitionEntity flowDefinitionEntity = spelExpressionService.getFlow(CLAIM_MODULE, dataMap);
        if (flowDefinitionEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 找不到對應流程！");
        }
        String nextStatus = flowDefinitionEntity.getNextStatus();

        if (StringUtils.isEmpty(nextStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 查無下一個流程！");
        }

        // 當下流程 改失效
        current.setValid(ValidEnum.INVALID.getCode());
        claimMainStatusService.update(current);

        // 寫入新流程
        ClaimMainStatusEntity claimMainStatusEntity = new ClaimMainStatusEntity();
        claimMainStatusEntity.setCaseUuid(UUID.randomUUID().toString());
        claimMainStatusEntity.setMainUuid(current.getMainUuid());
        claimMainStatusEntity.setClientId(clientId);
        claimMainStatusEntity.setClaimSeq(claimSeq);
        claimMainStatusEntity.setModuleType(current.getModuleType());
        claimMainStatusEntity.setMainStatus(nextStatus);
        claimMainStatusEntity.setValid(ValidEnum.VALID.getCode());
        claimMainStatusEntity.setNote(flowChangeDTO.getNote());
        claimMainStatusEntity.setOwnerUser(ownerUser);
        claimMainStatusEntity.setProcessUser(processUser);
        claimMainStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimMainStatusEntity.setProcessTime(MliDateUtil.getTime());

        claimMainStatusService.insert(claimMainStatusEntity);

        // 取得 中文
        String mainStatusDesc = flowDefinitionService.getCliamStatusDesc(CLAIM_MODULE, nextStatus);
        String validDesc = ValidEnum.getDescByCode(claimMainStatusEntity.getValid());

        // 設定回傳資料
        ClaimMainStatusVO claimMainStatusVO = new ClaimMainStatusVO();
        BeanUtils.copyProperties(claimMainStatusEntity, claimMainStatusVO);
        claimMainStatusVO.setMainStatus(nextStatus + " " + mainStatusDesc);
        claimMainStatusVO.setValid(claimMainStatusVO.getValid() + " " + validDesc);

        return claimMainStatusVO;
    }


    /**
     * 理賠流程 上一關
     * @param flowChangeDTO 異動流程 Input
     */
    public ClaimMainStatusVO previousFlow(FlowChangeDTO flowChangeDTO) {
        // 初始資料檢查
        String clientId = flowChangeDTO.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        Integer claimSeq = flowChangeDTO.getClaimSeq();
        if (claimSeq == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }
        String ownerUser = flowChangeDTO.getOwnerUser();
        if (StringUtils.isEmpty(ownerUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：ownerUser 空白");
        }
        String processUser = flowChangeDTO.getProcessUser();
        if (StringUtils.isEmpty(processUser)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：processUser 空白");
        }

        // 取得目前案件
        ClaimMainStatusEntity current = claimMainStatusService.getCurrentData(clientId, claimSeq);

        // 檢查 案件是否已建立
        if (current == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 案件不存在！");
        }

        // 檢查 是否有 未結案子流程
        List<ClaimSubStatusEntity> claimSubStatusEntityList = claimSubStatusService.getValidData(clientId, claimSeq);
        if (!CollectionUtils.isEmpty(claimSubStatusEntityList)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 尚有子流程未結案！");
        }

        // 取得 當下流程
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", current.getMainStatus());

        FlowDefinitionEntity flowDefinitionEntity = spelExpressionService.getFlow(CLAIM_MODULE, dataMap);
        if (flowDefinitionEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 找不到對應流程！");
        }
        String previousStatus = flowDefinitionEntity.getPreviousStaus();

        if (StringUtils.isEmpty(previousStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 查無上一個流程！");
        }

        // 當下流程 改失效
        current.setValid(ValidEnum.INVALID.getCode());
        claimMainStatusService.update(current);

        // 寫入新流程
        ClaimMainStatusEntity claimMainStatusEntity = new ClaimMainStatusEntity();
        claimMainStatusEntity.setCaseUuid(UUID.randomUUID().toString());
        claimMainStatusEntity.setMainUuid(current.getMainUuid());
        claimMainStatusEntity.setClientId(clientId);
        claimMainStatusEntity.setClaimSeq(claimSeq);
        claimMainStatusEntity.setModuleType(current.getModuleType());
        claimMainStatusEntity.setMainStatus(previousStatus);
        claimMainStatusEntity.setValid(ValidEnum.VALID.getCode());
        claimMainStatusEntity.setNote(flowChangeDTO.getNote());
        claimMainStatusEntity.setOwnerUser(ownerUser);
        claimMainStatusEntity.setProcessUser(processUser);
        claimMainStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimMainStatusEntity.setProcessTime(MliDateUtil.getTime());

        claimMainStatusService.insert(claimMainStatusEntity);

        // 取得 中文
        String mainStatusDesc = flowDefinitionService.getCliamStatusDesc(CLAIM_MODULE, previousStatus);
        String validDesc = ValidEnum.getDescByCode(claimMainStatusEntity.getValid());

        // 設定回傳資料
        ClaimMainStatusVO claimMainStatusVO = new ClaimMainStatusVO();
        BeanUtils.copyProperties(claimMainStatusEntity, claimMainStatusVO);
        claimMainStatusVO.setMainStatus(previousStatus + " " + mainStatusDesc);
        claimMainStatusVO.setValid(claimMainStatusVO.getValid() + " " + validDesc);

        return claimMainStatusVO;
    }
}
