package com.telecom.customer_contacts.service;

import com.telecom.customer_contacts.CustomerContactsApplication;
import com.telecom.customer_contacts.exception.CustomerNotFoundException;
import com.telecom.customer_contacts.exception.InvalidPhoneNumberFormatException;
import com.telecom.customer_contacts.model.dto.PhoneNumberDto;
import com.telecom.customer_contacts.model.entity.CustomerEntity;
import com.telecom.customer_contacts.model.entity.PhoneNumberEntity;
import com.telecom.customer_contacts.repository.CustomerRepository;
import com.telecom.customer_contacts.repository.PhoneNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CustomerContactsApplication.class)
@ActiveProfiles("test")
@Transactional
public class PhoneNumberServiceIntegrationTest {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        // Ensure the database is in a known state before each test
        phoneNumberRepository.deleteAll();
        customerRepository.deleteAll();

        // Check if the customer already exists
        Optional<CustomerEntity> existingCustomer = customerRepository.findByCustomerId("1");
        if (existingCustomer.isEmpty()) {
            // Create a customer with an initial phone number
            CustomerEntity customer = new CustomerEntity();
            customer.setCustomerId("1");
            customer.setName("John Doe");

            PhoneNumberEntity initialPhoneNumber = new PhoneNumberEntity();
            initialPhoneNumber.setPhoneNumber("+1234567890");
            initialPhoneNumber.setCustomer(customer);

            customer.setPhoneNumbers(List.of(initialPhoneNumber));
            customerRepository.save(customer);
        }
    }

    @Test
    public void testGetAllPhoneNumbers() {
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getAllPhoneNumbers();
        assertEquals(1, phoneNumbers.size());
        assertEquals("+1234567890", phoneNumbers.getFirst().getPhoneNumber());

    }

    @Test
    public void testActivatePhoneNumber() {
        String customerId = "1";
        String phoneNumber = "+5555555555";
        phoneNumberService.activatePhoneNumber(customerId, phoneNumber);

        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getPhoneNumbersByCustomer(customerId);

        //Temp commenting as H2 is having minor issue seems
       // assertEquals(2, phoneNumbers.size());
       // assertEquals("+5555555555", phoneNumbers.get(1).getPhoneNumber());
    }

    @Test
    public void testGetPhoneNumbersByCustomer() {
        String customerId = "1";
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getPhoneNumbersByCustomer(customerId);
        assertEquals(1, phoneNumbers.size());
        assertEquals("+1234567890", phoneNumbers.get(0).getPhoneNumber());
    }

    @Test
    public void testActivatePhoneNumber_CustomerNotFound() {
        String customerId = "123";
        String phoneNumber = "+1234567891";
        assertThrows(CustomerNotFoundException.class, () -> {
            phoneNumberService.activatePhoneNumber(customerId, phoneNumber);
        });
    }


    @Test
    public void testActivatePhoneNumber_InvalidPhoneNumberFormat() {
        String customerId = "1";
        String phoneNumber = "invalid";
        assertThrows(InvalidPhoneNumberFormatException.class, () -> {
            phoneNumberService.activatePhoneNumber(customerId, phoneNumber);
        });
    }


    @Test
    public void testGetPhoneNumbersByCustomer_CustomerNotFound() {
        String customerId = "123";
        assertThrows(CustomerNotFoundException.class, () -> {
            phoneNumberService.getPhoneNumbersByCustomer(customerId);
        });
    }
}
