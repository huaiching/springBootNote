package com.mli.flow.service.crud;

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
     * 取得所有資料
     * @param clientId 客戶證號
     * @param claimSeq 建檔編號
     */
    public List<ClaimSubStatusEntity> getAllData(String clientId, Integer claimSeq) {
        String sql = "SELECT * FROM claim_sub_status " +
                "WHERE client_id = :clientId " +
                "  AND claim_seq = :claimSeq " +
                "ORDER BY process_date, process_time ";

        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientId);
        params.put("claimSeq", claimSeq);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ClaimSubStatusEntity.class));
    }

    /**
     * 取得有效資料
     * @param clientId 客戶證號
     * @param claimSeq 建檔編號
     */
    public List<ClaimSubStatusEntity> getValidData(String clientId, Integer claimSeq) {
        List<ClaimSubStatusEntity> claimSubStatusEntityList = getAllData(clientId, claimSeq);
        return claimSubStatusEntityList.stream()
                .filter(entity -> entity.getValid().equals("1"))
                .collect(Collectors.toList());
    }

    /**
     * 取得 最新一筆資料
     * @param subUuid 子流程 UUID
     */
    public ClaimSubStatusEntity getCurrentData(String subUuid) {
        String sql = "SELECT * FROM claim_sub_status " +
                "WHERE sub_uuid = :subUuid " +
                "ORDER BY process_date, process_time ";

        Map<String, Object> params = new HashMap<>();
        params.put("subUuid", subUuid);

        List<ClaimSubStatusEntity> claimSubStatusEntityList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ClaimSubStatusEntity.class));


        // 取得最新一筆資料
        return CollectionUtils.isEmpty(claimSubStatusEntityList) ? null :
                claimSubStatusEntityList.get(claimSubStatusEntityList.size()-1);
    }


}
