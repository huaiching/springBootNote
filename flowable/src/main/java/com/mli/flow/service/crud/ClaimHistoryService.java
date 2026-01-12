package com.mli.flow.service.crud;

import com.mli.flow.entity.ClaimHistoryEntity;
import com.mli.flow.entity.ClaimMainStatusEntity;
import com.mli.flow.repository.ClaimHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案件歷程表 CRUD
 */

@Service
public class ClaimHistoryService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private ClaimHistoryRepository claimHistoryRepository;

    /**
     * 新增
     * @param claimHistoryEntity entity
     */
    public void insert(ClaimHistoryEntity claimHistoryEntity) {
        claimHistoryRepository.save(claimHistoryEntity);
    }

    /**
     * 修改
     * @param claimHistoryEntity entity
     */
    public void update(ClaimHistoryEntity claimHistoryEntity) {
        claimHistoryRepository.save(claimHistoryEntity);
    }

    /**
     * 取得 案件歷史流程
     * @param clientId 客戶證號
     * @param clfpSeq 建檔編號
     */
    public List<ClaimHistoryEntity> getFlowHistory(String clientId, Integer clfpSeq) {
        String sql = "SELECT * FROM claim_history " +
                "WHERE client_id = :clientId " +
                "  AND clfp_seq = :clfpSeq " +
                "ORDER BY process_date, process_time ";

        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientId);
        params.put("clfpSeq", clfpSeq);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ClaimHistoryEntity.class));

    }
}
