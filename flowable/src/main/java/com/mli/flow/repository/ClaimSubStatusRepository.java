package com.mli.flow.repository;

import com.mli.flow.entity.ClaimSubStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimSubStatusRepository extends JpaRepository<ClaimSubStatusEntity, String> {
}
