package com.eazybytes.accounts.service.test;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import com.eazybytes.accounts.service.impl.AccountsServiceImpl;
import com.eazybytes.accounts.service.impl.CustomersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ProductServiceTest {
    private ProductService productService;
    private AccountsRepository accountsRepository = mock(AccountsRepository.class);
    private CustomerRepository customerRepository = mock(CustomerRepository.class);
    private IAccountsService accountsService = mock(AccountsServiceImpl.class);
    @BeforeEach
    void setup() {
        productService = new ProductService();
    }

    @Test
    void testCalculateDiscount_WhenPriceAbove1000() {
        int result = productService.calculateDiscount(1200);
        assertEquals(1001, result);
    }

    @Test
    void testCalculateDiscount_WhenPriceBelow1000() {
        int result = productService.calculateDiscount(800);
        assertEquals(800, result);
    }

    @Test
    void testFindById() {
        AccountsDto a = mock(AccountsDto.class);
        CustomerDto c = new CustomerDto("Madan Reddy", "tutor@eazybytes", "4354437687", a);
        doNothing().when(this.accountsService).createAccount(c);
        when(accountsService.fetchAccount("4354437687")).thenReturn(c);
        CustomerDto customerDto = accountsService.fetchAccount("4354437687");
        assertEquals("4354437687", customerDto.getMobileNumber());
    }
}
