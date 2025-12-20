package com.mli.flow.service.crud;

import com.mli.flow.entity.ClaimMainStatusEntity;
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
     * 取得 所有資料
     * @param clientId 客戶證號
     * @param claimSeq 建檔編號
     */
    public List<ClaimMainStatusEntity> getAllData(String clientId, Integer claimSeq) {
        String sql = "SELECT * FROM claim_main_status " +
                "WHERE client_id = :clientId " +
                "  AND claim_seq = :claimSeq " +
                "ORDER BY process_date, process_time ";

        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientId);
        params.put("claimSeq", claimSeq);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ClaimMainStatusEntity.class));
    }

    /**
     * 取得 有效資料
     * @param clientId 客戶證號
     * @param claimSeq 建檔編號
     */
    public ClaimMainStatusEntity getValidData(String clientId, Integer claimSeq) {
        List<ClaimMainStatusEntity> claimMainStatusEntityList = getAllData(clientId, claimSeq);
        return claimMainStatusEntityList.stream()
                .filter(entity -> entity.getValid().equals("1"))
                .findFirst().orElse(null);
    }

    /**
     * 取得 最新一筆資料
     * @param clientId 客戶證號
     * @param claimSeq 建檔編號
     */
    public ClaimMainStatusEntity getCurrentData(String clientId, Integer claimSeq) {
        List<ClaimMainStatusEntity> claimMainStatusEntityList = getAllData(clientId, claimSeq);
        // 取得最新一筆資料
        return CollectionUtils.isEmpty(claimMainStatusEntityList) ? null :
                claimMainStatusEntityList.get(claimMainStatusEntityList.size()-1);
    }
}
