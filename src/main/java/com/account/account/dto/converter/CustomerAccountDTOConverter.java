package com.account.account.dto.converter;

import com.account.account.dto.CustomerAccountDTO;
import com.account.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerAccountDTOConverter {

    private final TransactionDTOConverter transactionDtoConverter;

    public CustomerAccountDTOConverter(TransactionDTOConverter converter) {
        this.transactionDtoConverter = converter;
    }

    public CustomerAccountDTO convert(Account from) {
        return new CustomerAccountDTO(
                Objects.requireNonNull(from.getId()),
                from.getBalance(),
                from.getTransaction()
                        .stream()
                        .map(transactionDtoConverter::convert)
                        .collect(Collectors.toSet()),
                from.getCreationDate());
    }
}