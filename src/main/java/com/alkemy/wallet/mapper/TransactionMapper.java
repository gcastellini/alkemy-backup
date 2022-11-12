package com.alkemy.wallet.mapper;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.dto.TransactionPaymentDto;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.Transaction;
import com.alkemy.wallet.repository.AccountRepository;
import com.alkemy.wallet.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class TransactionMapper {

    @Autowired
 IAccountService service;
    public TransactionDto map(Transaction transaction){
        TransactionDto response = new TransactionDto();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setDescription(transaction.getDescription());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setUser(transaction.getUser());
        response.setAccount(transaction.getAccount());
        return response;
    }

    public Transaction map(TransactionDto transaction){
        Transaction entity = new Transaction();
        entity.setId(transaction.getId());
        entity.setAmount(transaction.getAmount());
        entity.setType(transaction.getType());
        entity.setDescription(transaction.getDescription());
        entity.setTransactionDate(transaction.getTransactionDate());
        return entity;
    }
    private ModelMapper modelMapper = new ModelMapper();

    public TransactionPaymentDto entityToDto(Transaction transaction) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TransactionPaymentDto result = this.modelMapper.map(transaction, TransactionPaymentDto.class);
        return result;
    }

    public Transaction dtoToEntity(TransactionPaymentDto transaction) {
        Transaction entity = new Transaction();
        entity.setAmount(transaction.getAmount());
        entity.setDescription(transaction.getDescription());
      return entity;
    }


}