package com.alkemy.wallet.repository;

import com.alkemy.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAll();
    Transaction findByid(long id);
    List<Transaction> findByUserId(long id);
    Transaction save(Transaction t);
    public List<Transaction> findByAccount(Long id);
    @Query(value = "SELECT * FROM skill_up_java.transactions as t WHERE t.account_id=  id and t.transaction_date = current_date()", nativeQuery = true)
    public List<Transaction> findByAccountIdAndTransactionDate(@Param("id") Long id);
}