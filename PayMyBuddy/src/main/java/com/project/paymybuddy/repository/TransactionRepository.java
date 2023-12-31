package com.project.paymybuddy.repository;

import com.project.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByGiverId(Integer id);

    Transaction findByGiverId(Integer id);
}
