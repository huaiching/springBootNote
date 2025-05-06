package com.example.api.controller;

import com.example.api.dto.ClntUpdate;
import com.example.api.entity.Clnt;
import com.example.api.repository.ClntRepository;
import com.example.api.service.ClntService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

@RestController
@RequestMapping("/api/Clnt")
@Tag(name = "Clnt Controller", description = "客戶資料檔 API 接口")
public class ClntController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClntService clntService;
    @Autowired
    private ClntRepository clntRepository;

    @Operation(summary = "根據主鍵 新增或更新 Clnt",
               description = "根據主鍵，若有資料則更新，無資料則新增",
               operationId = "save")
    @PostMapping("/save")
    public ResponseEntity<Clnt> save(@RequestBody Clnt entity) {
        Clnt savedEntity = clntService.save(entity);
        return ResponseEntity.ok(savedEntity);
    }

    @Operation(summary = "根據主鍵 大量 新增或更新 Clnt",
               description = "根據主鍵，若有資料則更新，無資料則新增",
               operationId = "saveAll")
    @PostMapping("/saveAll")
    public ResponseEntity<List<Clnt>> saveAll(@RequestBody List<Clnt> entityList) {
        List<Clnt> savedEntityList = clntService.saveAll(entityList);
        return ResponseEntity.ok(savedEntityList);
    }

    @Operation(summary = "單筆更新 Clnt",
               description = "單筆新增 Clnt 資料",
               operationId = "update")
    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody ClntUpdate entityUpdate) {
        clntRepository.update(entityUpdate.getClntOri(), entityUpdate.getClntNew());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "根據主鍵 查詢 Clnt",
               description = "根據主鍵查詢 Clnt 資料",
               operationId = "findById")
    @GetMapping("/findById")
    public ResponseEntity<Clnt> findById(@RequestParam String clietId) {
        Clnt entity = clntService.findById(clietId);
        if (entity == null) {
            return ResponseEntity.ok(null); // 回傳 HTTP 200 OK 且 資料為 null
        }
        return ResponseEntity.ok(entity);  // 回傳 HTTP 200 OK 和資料
    }

    @Operation(summary = "根據主鍵 刪除 Clnt 資料",
               description = "根據主鍵刪除 Clnt 資料",
               operationId = "deleteById")
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam String clietId) {
        clntService.deleteById(clietId);
        return ResponseEntity.ok().build();
    }
}
