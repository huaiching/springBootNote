package com.example.api.repository;

import com.example.api.entity.Addr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddrRepository extends JpaRepository<Addr, Addr.AddrKey> {
}
