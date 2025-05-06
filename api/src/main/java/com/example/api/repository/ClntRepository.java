package com.example.api.repository;

import com.example.api.entity.Clnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClntRepository extends JpaRepository<Clnt, String>, ClntCustomRepository{
}
