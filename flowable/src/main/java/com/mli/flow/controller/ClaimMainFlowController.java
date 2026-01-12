package com.mli.flow.controller;

import com.mli.flow.dto.FlowChangeDTO;
import com.mli.flow.dto.FlowCreateDTO;
import com.mli.flow.service.ClaimMainFlowService;
import com.mli.flow.vo.ClaimMainStatusVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/claimMainFlowController")
@Tag(name = "Claim Main Flow Controller", description = "理賠 主流程 API")
public class ClaimMainFlowController {
    @Autowired
    private ClaimMainFlowService claimMainFlowService;

    @PostMapping("/createFlow/v1")
    @Operation(summary = "新增案件", description = "新增案件")
    public ResponseEntity<ClaimMainStatusVO> createFlow(@RequestBody FlowCreateDTO flowCreateDTO) {
        return ResponseEntity.ok(claimMainFlowService.createFlow(flowCreateDTO));
    }

    @PostMapping("/nextFlow/v1")
    @Operation(summary = "下一關", description = "下一關")
    public ResponseEntity<ClaimMainStatusVO> nextFlow(@RequestBody FlowChangeDTO flowChangeDTO) {
        return ResponseEntity.ok(claimMainFlowService.nextFlow(flowChangeDTO));
    }

    @PostMapping("/previousFlow/v1")
    @Operation(summary = "上一關", description = "上一關")
    public ResponseEntity<ClaimMainStatusVO> previousFlow(@RequestBody FlowChangeDTO flowChangeDTO) {
        return ResponseEntity.ok(claimMainFlowService.previousFlow(flowChangeDTO));
    }

}
