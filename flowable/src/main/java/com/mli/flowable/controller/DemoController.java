package com.mli.flowable.controller;

import com.mli.flowable.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
@Tag(name = "Flowable Demo Controller", description = "Flowable 範例 API")
public class DemoController {
    @Autowired
    private FlowableCaseAdvanceService flowableCaseAdvanceService;
    @Autowired
    private FlowableCaseCreateService flowableCaseCreateService;
    @Autowired
    private FlowableCaseHistoryService flowableCaseHistoryService;
    @Autowired
    private FlowableCaseQueryService flowableCaseQueryService;
    @Autowired
    private FlowableCaseRollbackService flowableCaseRollbackService;

    @GetMapping("/createCase")
    @Operation(summary = "新增案件")
    public ResponseEntity<String> createCase(@RequestParam String caseNo, @RequestParam String assignee) {
        String output = flowableCaseCreateService.createCase(caseNo, assignee);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/queryCase")
    @Operation(summary = "查詢案件目前資訊")
    public ResponseEntity<Map<String, Object>> queryCase(@RequestParam String caseNo) {
        Map<String, Object> output = flowableCaseQueryService.queryCase(caseNo);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/advanceCase")
    @Operation(summary = "送至下一關")
    public ResponseEntity<Map<String, String>> advanceCase(@RequestParam String caseNo) {
        Map<String, String> output = flowableCaseAdvanceService.advanceCase(caseNo);
        return ResponseEntity.ok(output);
    }


    @GetMapping("/toConsult")
    @Operation(summary = "送至照會")
    public ResponseEntity<Map<String, String>> toConsult(@RequestParam String caseNo) {
        Map<String, String> output = flowableCaseAdvanceService.toConsult(caseNo);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/rollbackCase")
    @Operation(summary = "返回上一關")
    public ResponseEntity<List<Map<String, String>>> rollbackCase(@RequestParam String caseNo) {
        List<Map<String, String>> output = flowableCaseRollbackService.rollbackCase(caseNo);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/queryCaseFullHistory")
    @Operation(summary = "根據 caseNo 查詢案件完整歷史")
    public ResponseEntity<List<Map<String, Object>>> queryCaseFullHistory(@RequestParam String caseNo) {
        List<Map<String, Object>> output = flowableCaseHistoryService.queryCaseFullHistory(caseNo);
        return ResponseEntity.ok(output);
    }
}
