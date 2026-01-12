package com.mli.flow.controller;

import com.mli.flow.dto.ClientIdAndClfpSeqDTO;
import com.mli.flow.service.ClaimHistoryFlowService;
import com.mli.flow.vo.ClaimHistoryVO;
import com.mli.flow.vo.ClaimMainStatusVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/claimHistoryController")
@Tag(name = "Claim History Controller", description = "理賠 歷史流程 API")
public class ClaimHistoryController {
    @Autowired
    private ClaimHistoryFlowService claimHistoryFlowService;

    @PostMapping("/getClaimHistory/v1")
    @Operation(summary = "理賠歷史流程查詢", description = "理賠歷史流程查詢")
    public ResponseEntity<List<ClaimHistoryVO>> getClaimHistory(@RequestBody ClientIdAndClfpSeqDTO clientIdAndClfpSeqDTO) {
        return ResponseEntity.ok(claimHistoryFlowService.getClaimHistory(clientIdAndClfpSeqDTO));
    }

    @PostMapping("/getCurrentMainFlow/v1")
    @Operation(summary = "目前案件主流程", description = "目前案件主流程")
    public ResponseEntity<ClaimMainStatusVO> getCurrentMainFlow(@RequestBody ClientIdAndClfpSeqDTO clientIdAndClfpSeqDTO) {
        return ResponseEntity.ok(claimHistoryFlowService.getCurrentMainFlow(clientIdAndClfpSeqDTO));
    }

}
