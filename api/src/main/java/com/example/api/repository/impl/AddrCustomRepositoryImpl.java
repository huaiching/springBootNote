package com.example.api.repository.impl;

import com.example.api.entity.Addr;
import com.example.api.repository.AddrCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AddrCustomRepositoryImpl implements AddrCustomRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 單筆更新 addr <br/>
     * @param entityOri 變更前的 addr
     * @param entityNew 變更後的 addr
     */
    @Override
    @Transactional
    public void update(Addr entityOri, Addr entityNew) {
        // 建立 SQL
        String sql = "UPDATE addr " +
                "SET client_id = :clientIdNew " +
                "   ,addr_ind = :addrIndNew " +
                "   ,address = :addressNew " +
                "   ,tel = :telNew " +
                "WHERE client_id = :clientIdOri " +
                "  AND addr_ind = :addrIndOri " +
                "  AND address = :addressOri " +
                "  AND tel = :telOri ";
        // 填入 參數
        Map<String, Object> params = new HashMap<>();
        params.put("clientIdNew", entityNew.getClientId());
        params.put("addrIndNew", entityNew.getAddrInd());
        params.put("addressNew", entityNew.getAddress());
        params.put("telNew", entityNew.getTel());
        params.put("clientIdOri", entityOri.getClientId());
        params.put("addrIndOri", entityOri.getAddrInd());
        params.put("addressOri", entityOri.getAddress());
        params.put("telOri", entityOri.getTel());
        // 執行 方法
        namedParameterJdbcTemplate.update(sql, params);
    }


    /**
     * 模糊 進行 地址搜尋
     *
     * @param address 要搜尋的地址字串
     * @return 查詢到 Addr 資料清單
     */
    @Override
    public List<Addr> queryAddress(String address) {
        // 建立 SQL
        String sql = "SELECT * FROM addr " +
                "WHERE address LIKE :address";
        // 填入 參數
        Map<String, Object> params = new HashMap<>();
        params.put("address", "%" + address + "%");
        // 執行 方法
        List<Addr> addrList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Addr.class));

        return addrList;
    }
}
