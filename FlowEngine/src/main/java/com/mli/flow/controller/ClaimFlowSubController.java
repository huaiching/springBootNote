package com.mli.flow.controller;

import com.mli.flow.dto.SubFlowChangeDTO;
import com.mli.flow.dto.SubFlowCreateDTO;
import com.mli.flow.service.FlowSubService;
import com.mli.flow.vo.ClaimSubStatusVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/claimSubFlowController")
@Tag(name = "Claim Sub Flow Controller", description = "理賠 子流程 API")
public class ClaimFlowSubController {
    @Autowired
    private FlowSubService flowSubService;

    @PostMapping("/createFlow")
    @Operation(summary = "新增案件", description = "新增案件")
    public ResponseEntity<ClaimSubStatusVO> createFlow(@RequestBody SubFlowCreateDTO subFlowCreateDTO) {
        return ResponseEntity.ok(flowSubService.createFlow(subFlowCreateDTO));
    }

    @PostMapping("/nextFlow")
    @Operation(summary = "下一關", description = "下一關")
    public ResponseEntity<ClaimSubStatusVO> nextFlow(@RequestBody SubFlowChangeDTO subFlowChangeDTO) {
        return ResponseEntity.ok(flowSubService.nextFlow(subFlowChangeDTO));
    }

    @PostMapping("/previousFlow")
    @Operation(summary = "上一關", description = "上一關")
    public ResponseEntity<ClaimSubStatusVO> previousFlow(@RequestBody SubFlowChangeDTO subFlowChangeDTO) {
        return ResponseEntity.ok(flowSubService.previousFlow(subFlowChangeDTO));
    }

}
