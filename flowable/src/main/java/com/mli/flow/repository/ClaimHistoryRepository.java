package com.mli.flow.repository;

import com.mli.flow.entity.ClaimHistoryEntity;
import com.mli.flow.uniquekey.ClaimHistoryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimHistoryRepository extends JpaRepository<ClaimHistoryEntity, ClaimHistoryKey>{

}
