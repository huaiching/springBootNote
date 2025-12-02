package com.mli.flow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mli.flow.entity.FlowDefinitionEntity;

public interface FlowDefinitionRepository extends JpaRepository<FlowDefinitionEntity, Integer>{
}
