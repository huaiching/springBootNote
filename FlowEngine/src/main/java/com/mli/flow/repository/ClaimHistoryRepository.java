package com.mli.flow.repository;

import com.mli.flow.entity.ClaimHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimHistoryRepository extends JpaRepository<ClaimHistoryEntity, String>{
    List<ClaimHistoryEntity> findByClientIdAndClaimSeq(String clientId, Integer cliamSeq);
}
