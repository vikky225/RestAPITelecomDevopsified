package com.telecom.customer_contacts.service;

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
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceTest {

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

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
        when(modelMapper.map(phoneNumberEntity, PhoneNumberDto.class)).thenReturn(new PhoneNumberDto("+1234567890"));

        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getPhoneNumbersByCustomer(customerId);
        assertEquals(1, phoneNumbers.size());
        assertEquals("+1234567890", phoneNumbers.getFirst().getPhoneNumber());
    }

    @Test
    public void testActivatePhoneNumber() {
        String customerId = "1";
        String phoneNumber = "+1234567890";
        CustomerEntity customer = new CustomerEntity();
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));

        phoneNumberService.activatePhoneNumber(customerId, phoneNumber);

        verify(phoneNumberRepository, times(1)).save(any(PhoneNumberEntity.class));
    }
}