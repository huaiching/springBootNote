package com.mli.flow.service.crud;

import com.mli.flow.entity.ClaimMainStatusEntity;
import com.mli.flow.entity.ClaimSubStatusEntity;
import com.mli.flow.repository.ClaimSubStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 理賠子流程狀態表 CRUD
 */

@Service
public class ClaimSubStatusService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private ClaimSubStatusRepository claimSubStatusRepository;

    /**
     * 新增
     * @param claimSubStatusEntity entity
     */
    public void insert(ClaimSubStatusEntity claimSubStatusEntity) {
        claimSubStatusRepository.save(claimSubStatusEntity);
    }

    /**
     * 修改
     * @param claimSubStatusEntity entity
     */
    public void update(ClaimSubStatusEntity claimSubStatusEntity) {
        claimSubStatusRepository.save(claimSubStatusEntity);
    }

    /**
     * 刪除
     * @param claimSubStatusEntity 當前流程 UUID
     */
    public void delete(ClaimSubStatusEntity claimSubStatusEntity) {
        claimSubStatusRepository.delete(claimSubStatusEntity);
    }

    /**
     * 根據 子流程 UUID 取得 案件當前狀態
     * @param subUuid 子流程 UUID
     */
    public ClaimSubStatusEntity getCurrentBySubUuid(String subUuid) {
        String sql = "SELECT * FROM claim_sub_status " +
                "WHERE sub_uuid = :subUuid ";

        Map<String, Object> params = new HashMap<>();
        params.put("subUuid", subUuid);

        List<ClaimSubStatusEntity> claimSubStatusEntityList = namedParameterJdbcTemplate
                .query(sql, params, new BeanPropertyRowMapper<>(ClaimSubStatusEntity.class));
        return CollectionUtils.isEmpty(claimSubStatusEntityList) ? null : claimSubStatusEntityList.get(0);
    }

    /**
     * 取得 案件當前狀態 (目前進行中的子流程)
     * @param clientId 客戶證號
     * @param clfpSeq 建檔編號
     */
    public List<ClaimSubStatusEntity> getCurrent(String clientId, Integer clfpSeq) {
        String sql = "SELECT * FROM claim_sub_status " +
                "WHERE client_id = :clientId " +
                "  AND clfp_seq = :clfpSeq " +
                "ORDER BY process_date, process_time ";

        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientId);
        params.put("clfpSeq", clfpSeq);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ClaimSubStatusEntity.class));
    }
}
