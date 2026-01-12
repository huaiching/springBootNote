package com.mli.flow.service.crud;

import com.mli.flow.entity.FlowDefinitionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程定義表 CRUD
 */

@Service
public class FlowDefinitionService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得 流程定義表
     * @param moduleType 模組類型
     */
    public List<FlowDefinitionEntity> getFolwList(String moduleType) {
        String sql = "SELECT * FROM flow_definition " +
                     "WHERE module_type = :moduleType ";

        Map<String, Object> params = new HashMap<>();
        params.put("moduleType", moduleType);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(FlowDefinitionEntity.class));
    }

    /**
     * 取得 理賠流程節點
     * @param moduleType 模組類型
     * @param status 流程節點
     */
    public FlowDefinitionEntity getFolw(String moduleType, String status){
        String sql = "SELECT * FROM flow_definition " +
                "WHERE module_type = :moduleType " +
                "  AND current_status = :status";

        Map<String, Object> params = new HashMap<>();
        params.put("moduleType", moduleType);
        params.put("status", status);

        List<FlowDefinitionEntity> flowList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(FlowDefinitionEntity.class));

        return CollectionUtils.isEmpty(flowList) ? null : flowList.get(0);
    }

    /**
     * 取得 理賠流程節點中文
     * @param moduleType 模組類型
     * @param status 流程節點
     */
    public String getCliamStatusDesc(String moduleType, String status){

        FlowDefinitionEntity flow = getFolw(moduleType, status);

        return flow == null ? "" : flow.getCurrentStatusDesc();
    }
}
