package com.example.api.repository;

import com.example.api.entity.Clnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClntRepository extends JpaRepository<Clnt, String> {

    @Query(value = "SELECT * FROM clnt " +
                   "WHERE client_id IN :clientIdList", nativeQuery = true)
    List<Clnt> queryClntByClientIdList(@Param("clientIdList") List<String> clientIdList);
}
