package com.account.account.dto.converter;

import com.account.account.dto.AccountCustomerDTO;
import com.account.account.dto.CustomerDTO;
import com.account.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerDTOConverter {

    private final CustomerAccountDTOConverter CustomerAccountDTOConverter;

    public CustomerDTOConverter(CustomerAccountDTOConverter converter) {
        this.CustomerAccountDTOConverter = converter;
    }

    public AccountCustomerDTO convertToAccountCustomer(Optional<Customer> from) {
        return from.map(f -> new AccountCustomerDTO(f.getId(), f.getName(), f.getSurname())).orElse(null);
    }

    public CustomerDTO convertToCustomerDTO(Customer from) {
        return new CustomerDTO(
                from.getId(),
                from.getName(),
                from.getSurname(),
                from.getAccounts()
                        .stream()
                        .map(CustomerAccountDTOConverter::convert)
                        .collect(Collectors.toSet()));
    }
}
