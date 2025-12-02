package com.mli.flow.controller;

import com.mli.flow.entity.ClaimHistoryEntity;
import com.mli.flow.entity.ClaimStatusEntity;
import com.mli.flow.service.ClaimFlowService;
import com.mli.flow.vo.ClaimHistoryVo;
import com.mli.flow.vo.ClaimStatusVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/claimFlow")
@Tag(name = "Claim Flow Controller", description = "理賠流程引擎 API")
public class ClaimFlowController {
    @Autowired
    private ClaimFlowService claimFlowService;

    @GetMapping("/createClaimStatus")
    @Operation(summary = "新增案件", description = "新增案件")
    public ResponseEntity<ClaimStatusVo> createClaimStatus(@RequestParam String clientId, @RequestParam String claimSeq) {
        return ResponseEntity.ok(claimFlowService.createClaimStatus(clientId, claimSeq));
    }

    @GetMapping("/nextClaimStatus")
    @Operation(summary = "前往下一關", description = "前往下一關")
    public ResponseEntity<ClaimStatusVo> nextClaimStatus(@RequestParam String clientId, @RequestParam String claimSeq) {
        return ResponseEntity.ok(claimFlowService.nextClaimStatus(clientId, claimSeq));
    }

    @GetMapping("/prewClaimStatus")
    @Operation(summary = "返回上一關", description = "返回上一關")
    public ResponseEntity<ClaimStatusVo> prewClaimStatus(@RequestParam String clientId, @RequestParam String claimSeq) {
        return ResponseEntity.ok(claimFlowService.prewClaimStatus(clientId, claimSeq));
    }

    @GetMapping("/subClaimStatus")
    @Operation(summary = "送至照會", description = "送至照會")
    public ResponseEntity<ClaimStatusVo> subClaimStatus(@RequestParam String clientId, @RequestParam String claimSeq) {
        return ResponseEntity.ok(claimFlowService.subClaimStatus(clientId, claimSeq));
    }

    @GetMapping("/getClaimStatus")
    @Operation(summary = "取得 目前案件資訊", description = "取得 目前案件資訊")
    public ResponseEntity<ClaimStatusVo> getClaimStatus(@RequestParam String clientId, @RequestParam String claimSeq) {
        return ResponseEntity.ok(claimFlowService.getClaimStatus(clientId, claimSeq));
    }

    @GetMapping("/getClaimHistory")
    @Operation(summary = "取得 案件歷史資訊", description = "取得 案件歷史資訊")
    public ResponseEntity<List<ClaimHistoryVo>> getClaimHistory(@RequestParam String clientId, @RequestParam String claimSeq) {
        return ResponseEntity.ok(claimFlowService.getClaimHistory(clientId, claimSeq));
    }
}
