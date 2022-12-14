package com.alkemy.wallet.model;

import com.alkemy.wallet.util.Type;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "AMOUNT", nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", columnDefinition = "ENUM('income','payment','deposit')", nullable = false)
    private Type type;

    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TRANSACTION_DATE")
    private Timestamp transactionDate;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
    private Account account;
}


