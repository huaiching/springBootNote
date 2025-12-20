package com.mli.flow.controller;

import com.mli.flow.dto.ClientIdAndClaimSeqDTO;
import com.mli.flow.dto.FlowChangeDTO;
import com.mli.flow.dto.FlowCreateDTO;
import com.mli.flow.service.FlowHistoryService;
import com.mli.flow.service.FlowMainService;
import com.mli.flow.vo.ClaimFlowHistoryVO;
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
@RequestMapping("/claimFlowHistoryController")
@Tag(name = "Claim Flow History Controller", description = "理賠 歷史流程 API")
public class ClaimFlowHistoryController {
    @Autowired
    private FlowHistoryService flowHistoryService;

    @PostMapping("/getClaimFlowHistory")
    @Operation(summary = "理賠歷史流程查詢", description = "理賠歷史流程查詢")
    public ResponseEntity<List<ClaimFlowHistoryVO>> getClaimFlowHistory(@RequestBody ClientIdAndClaimSeqDTO clientIdAndClaimSeqDTO) {
        return ResponseEntity.ok(flowHistoryService.getClaimFlowHistory(clientIdAndClaimSeqDTO));
    }

}
