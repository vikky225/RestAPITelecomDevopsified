package com.telecom.customer_contacts.service;

import com.telecom.customer_contacts.converter.PhoneNumberConverter;
import com.telecom.customer_contacts.exception.CustomerNotFoundException;
import com.telecom.customer_contacts.exception.InvalidPhoneNumberFormatException;
import com.telecom.customer_contacts.exception.PhoneNumberAlreadyActivtedException;
import com.telecom.customer_contacts.model.dto.PhoneNumberDto;
import com.telecom.customer_contacts.model.entity.CustomerEntity;
import com.telecom.customer_contacts.model.entity.PhoneNumberEntity;
import com.telecom.customer_contacts.repository.CustomerRepository;
import com.telecom.customer_contacts.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PhoneNumberService {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");




    public List<PhoneNumberDto> getAllPhoneNumbers() {
        return phoneNumberRepository.findAll().stream()
                .map(PhoneNumberConverter::toDto)
                .collect(Collectors.toList());
    }



        public List<PhoneNumberDto> getPhoneNumbersByCustomer(String customerId) {
            CustomerEntity customer = customerRepository.findByCustomerId(customerId)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
            return customer.getPhoneNumbers().stream()
                    .map(phoneNumber -> new PhoneNumberDto(phoneNumber.getPhoneNumber()))
                    .collect(Collectors.toList());
        }



    public void activatePhoneNumber(String customerId, String phoneNumber) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new InvalidPhoneNumberFormatException("Invalid phone number format");
        }

        CustomerEntity customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        boolean phoneNumberExists = customer.getPhoneNumbers().stream()
                .anyMatch(pn -> pn.getPhoneNumber().equals(phoneNumber));

        if (phoneNumberExists) {
            throw new PhoneNumberAlreadyActivtedException("Phone number already activated");
        }

        PhoneNumberEntity newPhoneNumber = new PhoneNumberEntity();
        newPhoneNumber.setPhoneNumber(phoneNumber);
        newPhoneNumber.setCustomer(customer);

        phoneNumberRepository.save(newPhoneNumber);
    }
}







