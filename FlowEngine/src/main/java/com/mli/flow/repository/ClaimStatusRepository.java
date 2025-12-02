package com.mli.flow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mli.flow.entity.ClaimStatusEntity;

import java.util.List;

public interface ClaimStatusRepository extends JpaRepository<ClaimStatusEntity, String> {
    List<ClaimStatusEntity> findByClientIdAndClaimSeq(String clientId, String cliamSeq);
}
