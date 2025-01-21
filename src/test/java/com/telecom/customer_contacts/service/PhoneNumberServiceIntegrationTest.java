package com.telecom.customer_contacts.service;

import com.telecom.customer_contacts.CustomerContactsApplication;
import com.telecom.customer_contacts.model.dto.PhoneNumberDto;
import com.telecom.customer_contacts.repository.CustomerRepository;
import com.telecom.customer_contacts.repository.PhoneNumberRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Autowired
    private ModelMapper modelMapper;



    @Test
    public void testGetAllPhoneNumbers() {
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getAllPhoneNumbers();
        System.out.println("phone number is"+phoneNumbers);
        assertEquals(1, phoneNumbers.size());
        assertEquals("+1234567890", phoneNumbers.getFirst().getPhoneNumber());

    }

    @Test
    public void testActivatePhoneNumber() {
        String customerId = "1";
        String phoneNumber = "+0987654321";
        phoneNumberService.activatePhoneNumber(customerId, phoneNumber);
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getPhoneNumbersByCustomer(customerId);
        assertEquals(2, phoneNumbers.size());
        assertEquals(phoneNumber, phoneNumbers.get(1).getPhoneNumber());
    }
}
