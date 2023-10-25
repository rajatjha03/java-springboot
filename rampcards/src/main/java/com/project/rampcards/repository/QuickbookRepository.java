package com.project.rampcards.repository;

import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuickbookRepository extends JpaRepository<Quickbook,Integer> {

    @Query(
            value = "SELECT * FROM quickbook q WHERE q.category = :category ",
            nativeQuery = true)
    public Quickbook findByCategory(@Param("category") String category);
}
