package com.mli.flow.controller;

import com.mli.flow.dto.ClientIdAndClaimSeqDto;
import com.mli.flow.service.ClaimFlowService;
import com.mli.flow.vo.ClaimHistoryVo;
import com.mli.flow.vo.ClaimStatusVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/claimFlow")
@Tag(name = "Claim Flow Controller", description = "理賠流程引擎 API")
public class ClaimFlowController {
    @Autowired
    private ClaimFlowService claimFlowService;

    @PostMapping("/createClaimStatus")
    @Operation(summary = "新增案件", description = "新增案件")
    public ResponseEntity<ClaimStatusVo> createClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        return ResponseEntity.ok(claimFlowService.createClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
    }

    @PostMapping("/nextClaimStatus")
    @Operation(summary = "前往下一關", description = "前往下一關")
    public ResponseEntity<ClaimStatusVo> nextClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        return ResponseEntity.ok(claimFlowService.nextClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
    }

    @PostMapping("/prewClaimStatus")
    @Operation(summary = "返回上一關", description = "返回上一關")
    public ResponseEntity<ClaimStatusVo> prewClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        return ResponseEntity.ok(claimFlowService.prewClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
    }

    @PostMapping("/subClaimStatus")
    @Operation(summary = "送至照會", description = "送至照會")
    public ResponseEntity<ClaimStatusVo> subClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        return ResponseEntity.ok(claimFlowService.subClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
    }

    @PostMapping("/getClaimStatus")
    @Operation(summary = "取得 目前案件資訊", description = "取得 目前案件資訊")
    public ResponseEntity<ClaimStatusVo> getClaimStatus(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        return ResponseEntity.ok(claimFlowService.getClaimStatus(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
    }

    @PostMapping("/getClaimHistory")
    @Operation(summary = "取得 案件歷史資訊", description = "取得 案件歷史資訊")
    public ResponseEntity<List<ClaimHistoryVo>> getClaimHistory(@RequestBody ClientIdAndClaimSeqDto clientIdAndClaimSeqDto) {
        return ResponseEntity.ok(claimFlowService.getClaimHistory(clientIdAndClaimSeqDto.getClientId(), clientIdAndClaimSeqDto.getClaimSql()));
    }
}
