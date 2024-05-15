package com.account.account.dto.converter;

import com.account.account.dto.AccountDTO;
import com.account.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountDTOConverter {

    private final CustomerDTOConverter customerDTOConverter;
    private final TransactionDTOConverter transactionDTOConverter;

    public AccountDTOConverter(CustomerDTOConverter customerDTOConverter,
                               TransactionDTOConverter transactionDTOConverter) {
        this.customerDTOConverter = customerDTOConverter;
        this.transactionDTOConverter = transactionDTOConverter;
    }

    public AccountDTO convert(Account from) {
        return new AccountDTO(from.getId(),
                from.getBalance(),
                from.getCreationDate(),
                customerDTOConverter.convertToAccountCustomer(Optional.ofNullable(from.getCustomer())),
                Objects.requireNonNull(from.getTransaction())
                        .stream()
                        .map(transactionDTOConverter::convert)
                        .collect(Collectors.toSet()));
    }
}
