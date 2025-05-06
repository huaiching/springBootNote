package com.example.api.service.impl;

import com.example.api.entity.Addr;
import com.example.api.repository.AddrRepository;
import com.example.api.service.AddrService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AddrServiceImpl implements AddrService {
    @Autowired
    private AddrRepository addrRepository;


    /**
     * 根據主鍵 新增或更新 addr <br/>
     * 若有資料則更新，無資料則新增
     * @param entity 要新增或更新的 addr
     * @return 儲存後的實體物件
     */
    @Override
    @Transactional
    public Addr save(Addr entity) {
        return addrRepository.save(entity);
    }

    /**
     * 根據主鍵 大量 新增或更新 addr <br/>
     * 若有資料則更新，無資料則新增
     * @param entityList 要新增或更新的 addr 清單
     * @return 儲存後的實體物件清單
     */
    @Override
    @Transactional
    public List<Addr> saveAll(List<Addr> entityList) {
        return addrRepository.saveAll(entityList);
    }

    /**
     * 根據主鍵 查詢 addr
     * @param id 主鍵值
     * @return 查詢到的實體物件，若無則返回 null
     */
    @Override
    @Transactional(readOnly = true)
    public Addr findById(Addr.AddrKey id) {
        return addrRepository.findById(id).orElse(null);
    }

    /**
     * 根據主鍵 刪除 addr
     * @param id 主鍵值
     */
    @Override
    @Transactional
    public void deleteById(Addr.AddrKey id) {
        if (addrRepository.existsById(id)) {
            addrRepository.deleteById(id);
        }
    }

}
