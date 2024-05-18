package com.account.account.service;

import com.account.account.TestSupport;
import com.account.account.dto.AccountCustomerDTO;
import com.account.account.dto.AccountDTO;
import com.account.account.dto.CreateAccountRequest;
import com.account.account.dto.TransactionDTO;
import com.account.account.dto.converter.AccountDTOConverter;
import com.account.account.exception.CustomerNotFoundException;
import com.account.account.model.Account;
import com.account.account.model.Customer;
import com.account.account.model.Transaction;
import com.account.account.model.TransactionType;
import com.account.account.repository.AccountRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountServiceTest extends TestSupport {

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDTOConverter converter;

    private AccountService service;

    private final Customer customer = generateCustomer();
    private final AccountCustomerDTO customerDto = new AccountCustomerDTO("customer-id",
            "customer-name",
            "customer-surname");

    @BeforeEach
    public void setup() {
        accountRepository = mock(AccountRepository.class);
        customerService = mock(CustomerService.class);
        converter = mock(AccountDTOConverter.class);
        Clock clock = mock(Clock.class);

        service = new AccountService(accountRepository, customerService, converter, clock);

        when(clock.instant()).thenReturn(getCurrentInstant());
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
    }

    @Test
    public void testCreateAccount_whenCustomerIdExistsAndInitialCreditMoreThanZero_shouldCreateAccountWithTransaction()
    {

        CreateAccountRequest request = generateCreateAccountRequest(100);

        Account account = generateAccount(100);
        Transaction transaction = new Transaction(null, TransactionType.INITIAL,
                request.getInitialCredit(), getLocalDateTime(), account);
        account.getTransaction().add(transaction);

        TransactionDTO transactionDto = new TransactionDTO("", TransactionType.INITIAL,
                new BigDecimal(100), getLocalDateTime());
        AccountDTO expected = new AccountDTO("account-id", new BigDecimal(100),
                getLocalDateTime(), customerDto, Set.of(transactionDto));

        when(customerService.findCustomerById("customer-id")).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);

        when(converter.convert(account)).thenReturn(expected);

        AccountDTO result = service.createAccount(request);

        assertEquals(result, expected);

    }

    @Test
    public void testCreateAccount_whenCustomerIdExistsAndInitialCreditIsZero_shouldCreateAccountWithoutTransaction() {
        CreateAccountRequest request = generateCreateAccountRequest(0);

        Account account = generateAccount(0);
        AccountDTO expected = new AccountDTO("account-id", BigDecimal.ZERO, getLocalDateTime(),
                customerDto, Set.of());

        when(customerService.findCustomerById("customer-id")).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);
        when(converter.convert(account)).thenReturn(expected);

        AccountDTO result = service.createAccount(request);

        assertEquals(result, expected);
    }

    @Test
    public void testCreateAccount_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundException() {
        CreateAccountRequest request = generateCreateAccountRequest(0);

        when(customerService.findCustomerById("customer-id")).thenThrow(
                new CustomerNotFoundException("test-exception"));

        assertThrows(CustomerNotFoundException.class,
                () -> service.createAccount(request));

        verify(customerService).findCustomerById(request.getCustomerId());
        verifyNoInteractions(accountRepository);
        verifyNoInteractions(converter);
    }

    private Account generateAccount(int balance) {
        return new Account("", new BigDecimal(balance), getLocalDateTime(), customer, new HashSet<>());
    }

}