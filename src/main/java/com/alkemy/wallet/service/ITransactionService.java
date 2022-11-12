package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.dto.basicDto.UserBasicDTO;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.Transaction;
import com.alkemy.wallet.util.Type;

import java.util.List;

public interface ITransactionService {
    /**
     * This method saves a payment in the database.
     * @param transaction
     * @return Transaction
     * @throws Exception
     */
    public Transaction savePayment(Transaction transaction) throws Exception;

    /**
     * This method saves a deposit in the database.
     * @param transaction
     * @return
     * @throws Exception
     */
    public Transaction saveDeposit(Transaction transaction) throws Exception;

    /**
     *
     * @param id
     * @return List<Transaction>
     */
    public List<Transaction> findByAccount(Long id);

    public List<AccountDto>   listAccounts();

    TransactionDto sendUsd(long accountFromId, long userId, double amount, long accountToId, Type type) throws Exception;

    List<TransactionDto> listUserId(long id);

    TransactionDto listDetail(long id);

    List<UserBasicDTO> listUser();
    TransactionDto edit(TransactionDto t);
}