package com.mli.flow.service;

import com.mli.flow.constants.FlowTypeEnum;
import com.mli.flow.dto.ClientIdAndClfpSeqDTO;
import com.mli.flow.entity.ClaimHistoryEntity;
import com.mli.flow.entity.ClaimMainStatusEntity;
import com.mli.flow.exceptions.BusinessException;
import com.mli.flow.service.crud.ClaimHistoryService;
import com.mli.flow.service.crud.ClaimMainStatusService;
import com.mli.flow.service.crud.ClaimSubStatusService;
import com.mli.flow.service.crud.FlowDefinitionService;
import com.mli.flow.vo.ClaimHistoryVO;
import com.mli.flow.vo.ClaimMainStatusVO;
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
public class ClaimHistoryFlowService {
    @Autowired
    private FlowDefinitionService flowDefinitionService;
    @Autowired
    private ClaimMainStatusService claimMainStatusService;
    @Autowired
    private ClaimSubStatusService claimSubStatusService;
    @Autowired
    private ClaimHistoryService claimHistoryService;

    /**
     * 理賠歷史流程查詢
     * @param clientIdAndClfpSeqDTO 客戶證號 及 建檔編號
     */
    public List<ClaimHistoryVO> getClaimHistory(ClientIdAndClfpSeqDTO clientIdAndClfpSeqDTO) {
        // 初始資料檢查
        String clientId = clientIdAndClfpSeqDTO.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        Integer clfpSeq = clientIdAndClfpSeqDTO.getClfpSeq();
        if (clfpSeq == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clfpSeq 空白");
        }
        // 取得 歷史流程資料
        List<ClaimHistoryEntity> claimHistoryEntityList = claimHistoryService.getFlowHistory(clientId, clfpSeq);

        // 設定輸出
        List<ClaimHistoryVO> claimHistoryVOList = new ArrayList<>();

        // 資料 中文 設定
        claimHistoryEntityList.stream()
                .filter(entity -> entity.getFlowType().equals(FlowTypeEnum.MAIN.getCode()))
                .filter(entity -> StringUtils.isEmpty(entity.getSubUuid()))
                .forEach(mainEntity -> {
                    // 設定 流程類型 中文
                    String mainFlowType = mainEntity.getFlowType() + " " +
                            FlowTypeEnum.getDescByCode(mainEntity.getFlowType());
                    mainEntity.setFlowType(mainFlowType);
                    // 設定 主流程 目前節點 中文
                    String mainStatus = mainEntity.getMainStatus() + " " +
                            flowDefinitionService.getCliamStatusDesc(mainEntity.getModuleType(), mainEntity.getMainStatus());
                    // 設定輸出
                    ClaimHistoryVO mainClaimHistoryVO = new ClaimHistoryVO();
                    BeanUtils.copyProperties(mainEntity, mainClaimHistoryVO);
                    mainClaimHistoryVO.setMainStatus(mainStatus);
                    claimHistoryVOList.add(mainClaimHistoryVO);

                    // 設定 子流程
                    claimHistoryEntityList.stream()
                            .filter(entity -> entity.getMainStatus().equals(mainEntity.getMainStatus()))
                            .filter(entity -> !StringUtils.isEmpty(entity.getSubUuid()))
                            .forEach(subEntity -> {
                                // 設定 流程類型 中文
                                String subFlowType = subEntity.getFlowType() + " " +
                                        FlowTypeEnum.getDescByCode(subEntity.getFlowType());
                                // 設定 子流程 目前節點 中文
                                String subStatus = subEntity.getSubStatus() + " " +
                                        flowDefinitionService.getCliamStatusDesc(subEntity.getModuleType(), subEntity.getSubStatus());
                                // 設定輸出
                                ClaimHistoryVO subClaimHistoryVO = new ClaimHistoryVO();
                                BeanUtils.copyProperties(subEntity, subClaimHistoryVO);
                                subClaimHistoryVO.setFlowType(subFlowType);
                                subClaimHistoryVO.setMainStatus(mainStatus);
                                subClaimHistoryVO.setSubStatus(subStatus);
                                claimHistoryVOList.add(subClaimHistoryVO);
                            });
                });
        // 排序
        Comparator<ClaimHistoryVO> comparator1 = Comparator.comparing(ClaimHistoryVO::getProcessDate);
        Comparator<ClaimHistoryVO> comparator2 = Comparator.comparing(ClaimHistoryVO::getProcessTime);
        claimHistoryVOList.sort(comparator1.thenComparing(comparator2));

        return claimHistoryVOList;
    }

    /**
     * 目前案件主流程
     * @param clientIdAndClfpSeqDTO 客戶證號 及 建檔編號
     */
    public ClaimMainStatusVO getCurrentMainFlow(ClientIdAndClfpSeqDTO clientIdAndClfpSeqDTO) {
        // 初始資料檢查
        String clientId = clientIdAndClfpSeqDTO.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clientId 空白");
        }
        Integer clfpSeq = clientIdAndClfpSeqDTO.getClfpSeq();
        if (clfpSeq == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "輸入參數錯誤：clfpSeq 空白");
        }

        ClaimMainStatusEntity current = claimMainStatusService.getCurrent(clientId, clfpSeq);

        // 取得 中文
        String status = current.getStatus() + " " +
                flowDefinitionService.getCliamStatusDesc(current.getModuleType(), current.getStatus());

        // 設定回傳資料
        ClaimMainStatusVO claimMainStatusVO = new ClaimMainStatusVO();
        BeanUtils.copyProperties(current, claimMainStatusVO);
        claimMainStatusVO.setStatus(status);

        return claimMainStatusVO;
    }
}
