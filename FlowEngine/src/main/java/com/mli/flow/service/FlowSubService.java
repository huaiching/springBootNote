package com.mli.flow.service;

import com.mli.flow.constants.ValidEnum;
import com.mli.flow.dto.FlowChangeDTO;
import com.mli.flow.dto.FlowCreateDTO;
import com.mli.flow.dto.SubFlowChangeDTO;
import com.mli.flow.dto.SubFlowCreateDTO;
import com.mli.flow.entity.ClaimMainStatusEntity;
import com.mli.flow.entity.ClaimSubStatusEntity;
import com.mli.flow.entity.FlowDefinitionEntity;
import com.mli.flow.exceptions.BusinessException;
import com.mli.flow.service.crud.ClaimMainStatusService;
import com.mli.flow.service.crud.ClaimSubStatusService;
import com.mli.flow.service.crud.FlowDefinitionService;
import com.mli.flow.util.MliDateUtil;
import com.mli.flow.vo.ClaimMainStatusVO;
import com.mli.flow.vo.ClaimSubStatusVO;
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
 * 流程引擎 子流程 相關邏輯
 */

@Service
public class FlowSubService {
    @Autowired
    private SpelExpressionService spelExpressionService;
    @Autowired
    private ClaimMainStatusService claimMainStatusService;
    @Autowired
    private ClaimSubStatusService claimSubStatusService;
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
        Integer claimSeq = subFlowCreateDTO.getClaimSeq();
        if (claimSeq == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
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
        ClaimMainStatusEntity mainStatusEntity = claimMainStatusService.getCurrentData(clientId, claimSeq);
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
        claimSubStatusEntity.setClaimSeq(claimSeq);
        claimSubStatusEntity.setModuleType(moduleType);
        claimSubStatusEntity.setMainStatus(mainStatusEntity.getMainStatus());
        claimSubStatusEntity.setSubStatus(nextStatus);
        claimSubStatusEntity.setValid(ValidEnum.VALID.getCode());
        claimSubStatusEntity.setNote("");
        claimSubStatusEntity.setOwnerUser(ownerUser);
        claimSubStatusEntity.setProcessUser(processUser);
        claimSubStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimSubStatusEntity.setProcessTime(MliDateUtil.getTime());

        claimSubStatusService.insert(claimSubStatusEntity);

        // 取得 中文
        String mainStatusDesc = flowDefinitionService.getCliamStatusDesc(mainStatusEntity.getModuleType(), mainStatusEntity.getMainStatus());
        String subStatusDesc = flowDefinitionService.getCliamStatusDesc(moduleType, nextStatus);
        String validDesc = ValidEnum.getDescByCode(claimSubStatusEntity.getValid());

        // 設定回傳資料
        ClaimSubStatusVO claimSubStatusVO = new ClaimSubStatusVO();
        BeanUtils.copyProperties(claimSubStatusEntity, claimSubStatusVO);
        claimSubStatusVO.setMainStatus(mainStatusEntity.getMainStatus() + " " + mainStatusDesc);
        claimSubStatusVO.setSubStatus(nextStatus + " " + subStatusDesc);
        claimSubStatusVO.setValid(claimSubStatusVO.getValid() + " " + validDesc);

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
        ClaimSubStatusEntity current = claimSubStatusService.getCurrentData(subUuid);
        if (current == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 案件不存在！");
        }

        String moduleType = current.getModuleType();

        // 取得 主流程
        ClaimMainStatusEntity mainStatusEntity = claimMainStatusService.getCurrentData(current.getClientId(), current.getClaimSeq());
        if (mainStatusEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件 執行錯誤: 主流程不存在！");
        }

        // 取得 當下流程
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", current.getSubStatus());

        FlowDefinitionEntity flowDefinitionEntity = spelExpressionService.getFlow(moduleType, dataMap);
        if (flowDefinitionEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 找不到對應流程！");
        }
        String nextStatus = flowDefinitionEntity.getNextStatus();

        if (StringUtils.isEmpty(nextStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 查無下一個流程！");
        }

        // 當下流程 改失效
        current.setValid(ValidEnum.INVALID.getCode());
        claimSubStatusService.update(current);

        // 子流程 最後一關 設定為 無效
        String vaild = ValidEnum.VALID.getCode();
        FlowDefinitionEntity nextFlow = flowDefinitionService.getFolw(moduleType, nextStatus);
        if (nextFlow.getNextStatus() == null) {
            vaild = ValidEnum.INVALID.getCode();
        }

        // 寫入新流程
        ClaimSubStatusEntity claimSubStatusEntity = new ClaimSubStatusEntity();
        claimSubStatusEntity.setCaseUuid(UUID.randomUUID().toString());
        claimSubStatusEntity.setMainUuid(current.getMainUuid());
        claimSubStatusEntity.setSubUuid(current.getSubUuid());
        claimSubStatusEntity.setClientId(current.getClientId());
        claimSubStatusEntity.setClaimSeq(current.getClaimSeq());
        claimSubStatusEntity.setModuleType(moduleType);
        claimSubStatusEntity.setMainStatus(current.getMainStatus());
        claimSubStatusEntity.setSubStatus(nextStatus);
        claimSubStatusEntity.setValid(vaild);
        claimSubStatusEntity.setNote(subFlowChangeDTO.getNote());
        claimSubStatusEntity.setOwnerUser(ownerUser);
        claimSubStatusEntity.setProcessUser(processUser);
        claimSubStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimSubStatusEntity.setProcessTime(MliDateUtil.getTime());

        claimSubStatusService.insert(claimSubStatusEntity);

        // 取得 中文
        String mainStatusDesc = flowDefinitionService.getCliamStatusDesc(mainStatusEntity.getModuleType(), mainStatusEntity.getMainStatus());
        String subStatusDesc = flowDefinitionService.getCliamStatusDesc(moduleType, nextStatus);
        String validDesc = ValidEnum.getDescByCode(claimSubStatusEntity.getValid());

        // 設定回傳資料
        ClaimSubStatusVO claimSubStatusVO = new ClaimSubStatusVO();
        BeanUtils.copyProperties(claimSubStatusEntity, claimSubStatusVO);
        claimSubStatusVO.setMainStatus(current.getMainStatus() + " " + mainStatusDesc);
        claimSubStatusVO.setSubStatus(nextStatus + " " + subStatusDesc);
        claimSubStatusVO.setValid(claimSubStatusVO.getValid() + " " + validDesc);

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
        ClaimSubStatusEntity current = claimSubStatusService.getCurrentData(subUuid);
        if (current == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "下一關 執行錯誤: 案件不存在！");
        }

        String moduleType = current.getModuleType();

        // 取得 主流程
        ClaimMainStatusEntity mainStatusEntity = claimMainStatusService.getCurrentData(current.getClientId(), current.getClaimSeq());
        if (mainStatusEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "新增案件 執行錯誤: 主流程不存在！");
        }

        // 取得 當下流程
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", current.getSubStatus());

        FlowDefinitionEntity flowDefinitionEntity = spelExpressionService.getFlow(moduleType, dataMap);
        if (flowDefinitionEntity == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 找不到對應流程！");
        }
        String previousStatus = flowDefinitionEntity.getPreviousStaus();

        if (StringUtils.isEmpty(previousStatus)) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "上一關 執行錯誤: 查無上一個流程！");
        }

        // 當下流程 改失效
        current.setValid(ValidEnum.INVALID.getCode());
        claimSubStatusService.update(current);

        // 寫入新流程
        ClaimSubStatusEntity claimSubStatusEntity = new ClaimSubStatusEntity();
        claimSubStatusEntity.setCaseUuid(UUID.randomUUID().toString());
        claimSubStatusEntity.setMainUuid(current.getMainUuid());
        claimSubStatusEntity.setSubUuid(current.getSubUuid());
        claimSubStatusEntity.setClientId(current.getClientId());
        claimSubStatusEntity.setClaimSeq(current.getClaimSeq());
        claimSubStatusEntity.setModuleType(moduleType);
        claimSubStatusEntity.setMainStatus(current.getMainStatus());
        claimSubStatusEntity.setSubStatus(previousStatus);
        claimSubStatusEntity.setValid(ValidEnum.VALID.getCode());
        claimSubStatusEntity.setNote(subFlowChangeDTO.getNote());
        claimSubStatusEntity.setOwnerUser(ownerUser);
        claimSubStatusEntity.setProcessUser(processUser);
        claimSubStatusEntity.setProcessDate(MliDateUtil.getToday());
        claimSubStatusEntity.setProcessTime(MliDateUtil.getTime());

        claimSubStatusService.insert(claimSubStatusEntity);

        // 取得 中文
        String mainStatusDesc = flowDefinitionService.getCliamStatusDesc(mainStatusEntity.getModuleType(), mainStatusEntity.getMainStatus());
        String subStatusDesc = flowDefinitionService.getCliamStatusDesc(moduleType, previousStatus);
        String validDesc = ValidEnum.getDescByCode(claimSubStatusEntity.getValid());

        // 設定回傳資料
        ClaimSubStatusVO claimSubStatusVO = new ClaimSubStatusVO();
        BeanUtils.copyProperties(claimSubStatusEntity, claimSubStatusVO);
        claimSubStatusVO.setMainStatus(current.getMainStatus() + " " + mainStatusDesc);
        claimSubStatusVO.setSubStatus(previousStatus + " " + subStatusDesc);
        claimSubStatusVO.setValid(claimSubStatusVO.getValid() + " " + validDesc);

        return claimSubStatusVO;
    }
}
