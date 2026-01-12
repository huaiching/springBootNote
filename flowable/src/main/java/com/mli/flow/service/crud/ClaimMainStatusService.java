package com.mli.flow.service.crud;

import com.mli.flow.entity.ClaimMainStatusEntity;
import com.mli.flow.entity.ClaimSubStatusEntity;
import com.mli.flow.repository.ClaimMainStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 理賠主流程狀態表 CRUD
 */

@Service
public class ClaimMainStatusService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private ClaimMainStatusRepository claimMainStatusRepository;

    /**
     * 新增
     * @param claimMainStatusEntity entity
     */
    public void insert(ClaimMainStatusEntity claimMainStatusEntity) {
        claimMainStatusRepository.save(claimMainStatusEntity);
    }

    /**
     * 修改
     * @param claimMainStatusEntity entity
     */
    public void update(ClaimMainStatusEntity claimMainStatusEntity) {
        claimMainStatusRepository.save(claimMainStatusEntity);
    }

    /**
     * 刪除
     * @param claimMainStatusEntity 當前流程 UUID
     */
    public void delete(ClaimMainStatusEntity claimMainStatusEntity) {
        claimMainStatusRepository.delete(claimMainStatusEntity);
    }

    /**
     * 根據 主流程 UUID 取得 案件當前狀態
     * @param mainUuid 主流程 UUID
     */
    public ClaimMainStatusEntity getCurrentByMainUuid(String mainUuid) {
        String sql = "SELECT * FROM claim_main_status " +
                "WHERE main_uuid = :mainUuid ";

        Map<String, Object> params = new HashMap<>();
        params.put("mainUuid", mainUuid);

        List<ClaimMainStatusEntity> claimMainStatusEntityList = namedParameterJdbcTemplate
                .query(sql, params, new BeanPropertyRowMapper<>(ClaimMainStatusEntity.class));
        return CollectionUtils.isEmpty(claimMainStatusEntityList) ? null : claimMainStatusEntityList.get(0);
    }

    /**
     * 取得 案件當前狀態
     * @param clientId 客戶證號
     * @param clfpSeq 建檔編號
     */
    public ClaimMainStatusEntity getCurrent(String clientId, Integer clfpSeq) {
        String sql = "SELECT * FROM claim_main_status " +
                "WHERE client_id = :clientId " +
                "  AND clfp_seq = :clfpSeq " +
                "ORDER BY process_date, process_time ";

        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientId);
        params.put("clfpSeq", clfpSeq);

        List<ClaimMainStatusEntity> claimMainStatusEntityList = namedParameterJdbcTemplate
                .query(sql, params, new BeanPropertyRowMapper<>(ClaimMainStatusEntity.class));

        return CollectionUtils.isEmpty(claimMainStatusEntityList) ? null :
                claimMainStatusEntityList.get(0);
    }
}
