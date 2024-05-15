package com.account.account.service;

import com.account.account.dto.AccountDTO;
import com.account.account.dto.converter.AccountDTOConverter;
import com.account.account.dto.createAccountRequest;
import com.account.account.model.Account;
import com.account.account.model.Customer;
import com.account.account.model.Transaction;
import com.account.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDTOConverter converter;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDTOConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.converter = converter;
    }

    public AccountDTO createAccount(createAccountRequest createAccountRequest) {
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(
                customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now());

        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(createAccountRequest.getInitialCredit(), account);
            account.getTransaction().add(transaction);
        }

        // transaction
        return converter.convert(accountRepository.save(account));
    }
}
