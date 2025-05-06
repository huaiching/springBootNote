package com.example.api.repository;

import com.example.api.entity.Addr;

import java.util.List;

public interface AddrCustomRepository {
    /**
     * 單筆更新 addr <br/>
     * @param entityOri 變更前的 addr
     * @param entityNew 變更後的 addr
     */
    void update(Addr entityOri, Addr entityNew);

    /**
     * 模糊 進行 地址搜尋
     * @param address 要搜尋的地址字串
     * @return 查詢到 Addr 資料清單
     */
    List<Addr> queryAddress(String address);
}
