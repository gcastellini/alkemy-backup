package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.dto.TransactionPaymentDto;
import com.alkemy.wallet.mapper.TransactionMapper;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.Transaction;
import com.alkemy.wallet.repository.AccountRepository;
import com.alkemy.wallet.repository.TransactionRepository;
import com.alkemy.wallet.service.ITransactionService;
import com.alkemy.wallet.service.IUserService;
import com.alkemy.wallet.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TransactionController {
    @Autowired
    private ITransactionService service;
    @Autowired
    private IUserService userService;

    @Autowired
    private TransactionRepository repo;

    @Autowired
    private AccountRepository accRepo;



    public TransactionController(ITransactionService service) {
        this.service = service;
    }


    @GetMapping("/transactions/{userId}")
    public List<TransactionDto> listTransactions(@PathVariable("userId") long id) {
        return service.listUserId(id);
    }

    @PostMapping("/transactions/:id{id}")
    public TransactionDto transactionDetail(@PathVariable("id") long id) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        TransactionDto transaction = new TransactionDto();
        long idUser = 0;
        for (int i = 0; i < service.listUser().size(); i++) {
            if (service.listUser().get(i).getEmail().equals(principal)) {
                idUser = service.listUser().get(i).getId();
            }
        }
        List<TransactionDto> listTransactions = service.listUserId(idUser);
        for (int j = 0; j < listTransactions.size(); j++) {
            if (listTransactions.get(j).getId() == id) {
                transaction = listTransactions.get(j);
            }
        }
        if (transaction != null) {

            return transaction;
        } else throw new RuntimeException();

    }

    @PatchMapping("/transactions/:id{id}")
    public TransactionDto editTransaction(@PathVariable("id") long id, @RequestBody String description) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
        TransactionDto transaction = service.listDetail(id);
        long idUser = 0;
        for (int i = 0; i < service.listUser().size(); i++) {
            if (service.listUser().get(i).getEmail().equals(principal)) {
                idUser = service.listUser().get(i).getId();
            }
        }
        List<TransactionDto> listTransactions = service.listUserId(idUser);
        if (listTransactions.contains(transaction)) {
            transaction.setDescription(description);
            return service.edit(transaction);
        } else throw new RuntimeException();
    }

    @PostMapping
    @RequestMapping("transactions/payment")
    public ResponseEntity<Object> payment(@RequestBody TransactionPaymentDto dto) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Transaction transaction = new TransactionMapper().dtoToEntity(dto);
        List<Account> listAccount = accRepo.findAll();
        Account acc = new Account();
        for (int i = 0; i < listAccount.size(); i++) {
            if (dto.getAccountId().equals(listAccount.get(i).getAccountId())) {
                acc = listAccount.get(i);
            }
        }
            transaction.setAccount(acc);
            transaction.setType(Type.payment);
            transaction.setUser(userService.findByEmail(email));
            TransactionDto result = new TransactionMapper().map(service.savePayment(transaction));
            return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @PostMapping
    @RequestMapping("transactions/deposit")
    public ResponseEntity<Object> deposit(@RequestBody TransactionPaymentDto dto, @RequestHeader(name="Authorization") String token) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Transaction transaction = new TransactionMapper().dtoToEntity(dto);
        transaction.setUser(userService.findByEmail(email));
        TransactionPaymentDto result = new TransactionMapper().entityToDto(service.saveDeposit(transaction));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}