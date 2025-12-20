package com.mli.flow.service;

import com.mli.flow.constants.StatusTypeEnum;
import com.mli.flow.constants.ValidEnum;
import com.mli.flow.dto.ClientIdAndClaimSeqDTO;
import com.mli.flow.entity.ClaimMainStatusEntity;
import com.mli.flow.entity.ClaimSubStatusEntity;
import com.mli.flow.exceptions.BusinessException;
import com.mli.flow.service.crud.ClaimMainStatusService;
import com.mli.flow.service.crud.ClaimSubStatusService;
import com.mli.flow.service.crud.FlowDefinitionService;
import com.mli.flow.vo.ClaimFlowHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 案件歷程查詢
 */

@Service
public class FlowHistoryService {
    @Autowired
    private ClaimMainStatusService claimMainStatusService;
    @Autowired
    private ClaimSubStatusService claimSubStatusService;
    @Autowired
    private FlowDefinitionService flowDefinitionService;

    /**
     * 理賠歷史流程查詢
     * @param clientIdAndClaimSeqDTO 客戶證號 及 建檔編號
     */
    public List<ClaimFlowHistoryVO> getClaimFlowHistory(ClientIdAndClaimSeqDTO clientIdAndClaimSeqDTO) {
        // 初始資料檢查
        String clientId = clientIdAndClaimSeqDTO.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        Integer claimSeq = clientIdAndClaimSeqDTO.getClaimSeq();
        if (claimSeq == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：claimSeq 空白");
        }
        // 取得 主流程資料
        List<ClaimMainStatusEntity> claimMainStatusEntityList = claimMainStatusService.getAllData(clientId, claimSeq);
        // 取得 子流程資料
        List<ClaimSubStatusEntity> claimSubStatusEntityList = claimSubStatusService.getAllData(clientId, claimSeq);

        // 資料設定
        List<ClaimFlowHistoryVO> claimFlowHistoryVOList = new ArrayList<>();
        // 主流程
        for (ClaimMainStatusEntity claimMainStatusEntity : claimMainStatusEntityList) {
            // 設定 中文
            String mainStatus = claimMainStatusEntity.getMainStatus() + " " +
                    flowDefinitionService.getCliamStatusDesc(claimMainStatusEntity.getModuleType(), claimMainStatusEntity.getMainStatus());
            String valid = claimMainStatusEntity.getValid() + " " +
                    ValidEnum.getDescByCode(claimMainStatusEntity.getValid());
            // 設定資料
            ClaimFlowHistoryVO claimFlowHistoryVO = new ClaimFlowHistoryVO();
            BeanUtils.copyProperties(claimMainStatusEntity, claimFlowHistoryVO);
            claimFlowHistoryVO.setStatusType(StatusTypeEnum.MAIN.getCode());
            claimFlowHistoryVO.setMainStatus(mainStatus);
            claimFlowHistoryVO.setValid(valid);

            claimFlowHistoryVOList.add(claimFlowHistoryVO);
        }
        // 子流程
        for (ClaimSubStatusEntity claimSubStatusEntity : claimSubStatusEntityList) {
            // 取得 父流程 流程中文
            String mainStatus = claimFlowHistoryVOList.stream()
                    .map(ClaimFlowHistoryVO::getMainStatus)
                    .filter(status -> status.matches(claimSubStatusEntity.getMainStatus() + ".*"))
                    .findFirst().orElse("");
            // 設定 中文
            String subStatus = claimSubStatusEntity.getSubStatus() + " " +
                    flowDefinitionService.getCliamStatusDesc(claimSubStatusEntity.getModuleType(), claimSubStatusEntity.getSubStatus());
            String valid = claimSubStatusEntity.getValid() + " " +
                    ValidEnum.getDescByCode(claimSubStatusEntity.getValid());
            // 設定資料
            ClaimFlowHistoryVO claimFlowHistoryVO = new ClaimFlowHistoryVO();
            BeanUtils.copyProperties(claimSubStatusEntity, claimFlowHistoryVO);
            claimFlowHistoryVO.setStatusType(StatusTypeEnum.SUB.getCode());
            claimFlowHistoryVO.setMainStatus(mainStatus);
            claimFlowHistoryVO.setSubStatus(subStatus);
            claimFlowHistoryVO.setValid(valid);

            claimFlowHistoryVOList.add(claimFlowHistoryVO);
        }
        // 排序
        Comparator<ClaimFlowHistoryVO> comparator1 = Comparator.comparing(ClaimFlowHistoryVO::getProcessDate);
        Comparator<ClaimFlowHistoryVO> comparator2 = Comparator.comparing(ClaimFlowHistoryVO::getProcessTime);
        claimFlowHistoryVOList.sort(comparator1.thenComparing(comparator2));

        return claimFlowHistoryVOList;
    }
}
