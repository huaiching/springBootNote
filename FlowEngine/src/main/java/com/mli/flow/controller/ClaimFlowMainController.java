package com.mli.flow.controller;

import com.mli.flow.dto.ClientIdAndClaimSeqDTO;
import com.mli.flow.dto.FlowChangeDTO;
import com.mli.flow.dto.FlowCreateDTO;
import com.mli.flow.service.FlowMainService;
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
@RequestMapping("/claimMainFlowController")
@Tag(name = "Claim Main Flow Controller", description = "理賠 主流程 API")
public class ClaimFlowMainController {
    @Autowired
    private FlowMainService flowMainService;

    @PostMapping("/createFlow")
    @Operation(summary = "新增案件", description = "新增案件")
    public ResponseEntity<ClaimMainStatusVO> createFlow(@RequestBody FlowCreateDTO flowCreateDTO) {
        return ResponseEntity.ok(flowMainService.createFlow(flowCreateDTO));
    }

    @PostMapping("/nextFlow")
    @Operation(summary = "下一關", description = "下一關")
    public ResponseEntity<ClaimMainStatusVO> nextFlow(@RequestBody FlowChangeDTO flowChangeDTO) {
        return ResponseEntity.ok(flowMainService.nextFlow(flowChangeDTO));
    }

    @PostMapping("/previousFlow")
    @Operation(summary = "上一關", description = "上一關")
    public ResponseEntity<ClaimMainStatusVO> previousFlow(@RequestBody FlowChangeDTO flowChangeDTO) {
        return ResponseEntity.ok(flowMainService.previousFlow(flowChangeDTO));
    }

    @PostMapping("/getCurrent")
    @Operation(summary = "目前案件流程", description = "目前案件流程")
    public ResponseEntity<ClaimMainStatusVO> getCurrent(@RequestBody ClientIdAndClaimSeqDTO clientIdAndClaimSeqDTO) {
        return ResponseEntity.ok(flowMainService.getCurrent(clientIdAndClaimSeqDTO));
    }

}
