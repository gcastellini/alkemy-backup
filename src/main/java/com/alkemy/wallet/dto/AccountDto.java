package com.alkemy.wallet.dto;

import com.alkemy.wallet.model.User;
import com.alkemy.wallet.util.CurrencyEnum;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccountDto {
    private User userId;
    private CurrencyEnum currency;
    private Double transactionLimit;
    private Double balance;
    private Timestamp creationDate;
    private Timestamp updateDate;
}
