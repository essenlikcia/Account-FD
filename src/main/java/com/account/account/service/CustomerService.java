package com.account.account.service;

import com.account.account.dto.CustomerDTO;
import com.account.account.dto.converter.CustomerDTOConverter;
import com.account.account.exception.CustomerNotFoundException;
import com.account.account.model.Customer;
import com.account.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDTOConverter converter;

    public CustomerService(CustomerRepository customerRepository, CustomerDTOConverter converter) {
        this.customerRepository = customerRepository;
        this.converter = converter;
    }

    protected Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer could not find by id: " + id));
    }

    public CustomerDTO getCustomerById(String customerId) {
        return converter.convertToCustomerDTO(findCustomerById(customerId));
    }
}
