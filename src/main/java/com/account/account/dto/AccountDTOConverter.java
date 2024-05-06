package com.account.account.dto;

import com.account.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOConverter {
    private final CustomerDTOConverter customerDTOConverter;

    public AccountDTOConverter(CustomerDTOConverter customerDTOConverter) {
        this.customerDTOConverter = customerDTOConverter;
    }

    public AccountDTO convert(Account from) {
        return new AccountDTO(from.getId(), from.getBalance(), from.getCreationDate(),
                customerDTOConverter.convertToAccountCustomer(from.getCustomer());
    }
}
