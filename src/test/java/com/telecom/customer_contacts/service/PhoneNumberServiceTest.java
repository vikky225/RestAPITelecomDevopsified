package com.telecom.customer_contacts.service;

import com.telecom.customer_contacts.exception.CustomerNotFoundException;
import com.telecom.customer_contacts.exception.InvalidPhoneNumberFormatException;
import com.telecom.customer_contacts.exception.PhoneNumberAlreadyActivtedException;
import com.telecom.customer_contacts.model.dto.PhoneNumberDto;
import com.telecom.customer_contacts.model.entity.CustomerEntity;
import com.telecom.customer_contacts.model.entity.PhoneNumberEntity;
import com.telecom.customer_contacts.repository.CustomerRepository;
import com.telecom.customer_contacts.repository.PhoneNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceTest {

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @Mock
    private CustomerRepository customerRepository;



    @BeforeEach
    public void setUp() {
        // No need to manually instantiate phoneNumberService, Mockito will handle it
    }

    @Test
    public void testGetAllPhoneNumbers() {
        when(phoneNumberRepository.findAll()).thenReturn(List.of());
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getAllPhoneNumbers();
        assertTrue(phoneNumbers.isEmpty());
    }

    @Test
    public void testGetPhoneNumbersByCustomer() {
        String customerId = "1";
        CustomerEntity customer = new CustomerEntity();
        PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
        phoneNumberEntity.setPhoneNumber("+1234567890");
        customer.setPhoneNumbers(List.of(phoneNumberEntity));
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));


        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getPhoneNumbersByCustomer(customerId);
        assertEquals(1, phoneNumbers.size());
        assertEquals("+1234567890", phoneNumbers.getFirst().getPhoneNumber());
    }

    @Test
    public void testActivatePhoneNumber() {
        String customerId = "1";
        String phoneNumber = "+1234567890";
        CustomerEntity customer = new CustomerEntity();
        customer.setPhoneNumbers(new ArrayList<>());
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));
        phoneNumberService.activatePhoneNumber(customerId, phoneNumber);
        verify(phoneNumberRepository, times(1)).save(any(PhoneNumberEntity.class));
    }

    @Test
    public void testGetPhoneNumbersByCustomer_CustomerNotFound() {
        String customerId = "123";
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            phoneNumberService.getPhoneNumbersByCustomer(customerId);
        });
    }

    @Test
    public void testActivatePhoneNumber_CustomerNotFound() {
        String customerId = "123";
        String phoneNumber = "1234567890";
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            phoneNumberService.activatePhoneNumber(customerId, phoneNumber);
        });
    }

    @Test
    public void testActivatePhoneNumber_InvalidPhoneNumberFormat() {
        String customerId = "123";
        String phoneNumber = "invalid";
        CustomerEntity customer = new CustomerEntity();
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));

        assertThrows(InvalidPhoneNumberFormatException.class, () -> {
            phoneNumberService.activatePhoneNumber(customerId, phoneNumber);
        });
    }


    @Test
    public void testActivatePhoneNumber_PhoneNumberAlreadyActivated() {
        String customerId = "123";
        String phoneNumber = "1234567890";
        CustomerEntity customer = new CustomerEntity();
        PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
        phoneNumberEntity.setPhoneNumber(phoneNumber);
        customer.setPhoneNumbers(List.of(phoneNumberEntity));
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));

        assertThrows(PhoneNumberAlreadyActivtedException.class, () -> {
            phoneNumberService.activatePhoneNumber(customerId, phoneNumber);
        });
}
}