package com.project.rampcards.repository;

import com.project.rampcards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Query(
            value = "SELECT * FROM transaction t WHERE t.name = :name ",
            nativeQuery = true)
    public List<Transaction> findAllByTransactionName(@Param("name") String name);
}
