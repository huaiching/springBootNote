package com.example.api.repository;

import com.example.api.entity.Addr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddrRepository extends JpaRepository<Addr, Addr.AddrKey>, AddrCustomRepository {

    /**
     * SELECT * FROM addr
     * HWERE client_id = :clientId
     */
    List<Addr> findByClientId(String clientId);
}
