package com.mli.flow.controller;

import com.mli.flow.dto.FlowCreateDTO;
import com.mli.flow.dto.SubFlowChangeDTO;
import com.mli.flow.dto.SubFlowCreateDTO;
import com.mli.flow.service.ClaimSubFlowService;
import com.mli.flow.vo.ClaimSubStatusVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/claimSubFlowController")
@Tag(name = "Claim Sub Flow Controller", description = "理賠 子流程 API")
public class ClaimSubFlowController {
    @Autowired
    private ClaimSubFlowService claimSubFlowService;

    @PostMapping("/createFlow")
    @Operation(summary = "新增案件", description = "新增案件")
    public ResponseEntity<ClaimSubStatusVO> createFlow(@RequestBody SubFlowCreateDTO subFlowCreateDTO) {
        return ResponseEntity.ok(claimSubFlowService.createFlow(subFlowCreateDTO));
    }

    @PostMapping("/createInquiry")
    @Operation(summary = "新增照會", description = "新增照會")
    public ResponseEntity<ClaimSubStatusVO> createInquiry(@RequestBody FlowCreateDTO flowCreateDTO) {
        SubFlowCreateDTO subFlowCreateDTO = new SubFlowCreateDTO();
        BeanUtils.copyProperties(flowCreateDTO, subFlowCreateDTO);
        subFlowCreateDTO.setModuleType("inquiry");
        return ResponseEntity.ok(claimSubFlowService.createFlow(subFlowCreateDTO));
    }

    @PostMapping("/nextFlow")
    @Operation(summary = "下一關", description = "下一關")
    public ResponseEntity<ClaimSubStatusVO> nextFlow(@RequestBody SubFlowChangeDTO subFlowChangeDTO) {
        return ResponseEntity.ok(claimSubFlowService.nextFlow(subFlowChangeDTO));
    }

    @PostMapping("/previousFlow")
    @Operation(summary = "上一關", description = "上一關")
    public ResponseEntity<ClaimSubStatusVO> previousFlow(@RequestBody SubFlowChangeDTO subFlowChangeDTO) {
        return ResponseEntity.ok(claimSubFlowService.previousFlow(subFlowChangeDTO));
    }

}
