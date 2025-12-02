package com.mli.flow.controller;

import com.mli.flow.dto.ClientIdAndClaimSeqDto;
import com.mli.flow.service.ClaimFlowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/claimFlow")
@Tag(name = "Claim Flow Controller", description = "理賠流程引擎 API")
public class ClaimFlowController {
    @Autowired
    private ClaimFlowService claimFlowService;

    @PostMapping("/createClaimStatus")
    @Operation(summary = "新增案件", description = "新增案件")
    public ResponseEntity<?> createClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        try {
            return ResponseEntity.ok(claimFlowService.createClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PostMapping("/nextClaimStatus")
    @Operation(summary = "前往下一關", description = "前往下一關")
    public ResponseEntity<?> nextClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        try {
            return ResponseEntity.ok(claimFlowService.nextClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PostMapping("/prewClaimStatus")
    @Operation(summary = "返回上一關", description = "返回上一關")
    public ResponseEntity<?> prewClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        try {
            return ResponseEntity.ok(claimFlowService.prewClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PostMapping("/subClaimStatus")
    @Operation(summary = "送至照會", description = "送至照會")
    public ResponseEntity<?> subClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        try {
            return ResponseEntity.ok(claimFlowService.subClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PostMapping("/getClaimStatus")
    @Operation(summary = "取得 目前案件資訊", description = "取得 目前案件資訊")
    public ResponseEntity<?> getClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        try {
            return ResponseEntity.ok(claimFlowService.getClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PostMapping("/getClaimHistory")
    @Operation(summary = "取得 案件歷史資訊", description = "取得 案件歷史資訊")
    public ResponseEntity<?> getClaimHistory(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        try {
            return ResponseEntity.ok(claimFlowService.getClaimHistory(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }
}
