package com.telecom.customer_contacts.service;

import com.telecom.customer_contacts.exception.CustomerNotFoundException;
import com.telecom.customer_contacts.exception.InvalidPhoneNumberFormatException;
import com.telecom.customer_contacts.exception.PhoneNumberAlreadyActivtedException;
import com.telecom.customer_contacts.model.dto.PhoneNumberDto;
import com.telecom.customer_contacts.model.entity.CustomerEntity;
import com.telecom.customer_contacts.model.entity.PhoneNumberEntity;
import com.telecom.customer_contacts.repository.CustomerRepository;
import com.telecom.customer_contacts.repository.PhoneNumberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository,
                              CustomerRepository customerRepository,
                              ModelMapper modelMapper) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        configureModelMapper();
    }


    public List<PhoneNumberDto> getAllPhoneNumbers() {
        return phoneNumberRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());



    }

    public List<PhoneNumberDto> getPhoneNumbersByCustomer(String customerId) {

        Optional<CustomerEntity> customer = customerRepository.findByCustomerId(customerId);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found for ID: " + customerId);
        }
        return customer.map(c -> c.getPhoneNumbers().stream() // Assuming getPhoneNumbers() returns a List
                        .map(this::mapToDto) // Assuming mapToDto converts PhoneNumberEntity to PhoneNumberDto
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private PhoneNumberDto mapToDto(PhoneNumberEntity entity) {
       System.out.print("entity is"+entity.getPhoneNumber());
       return modelMapper.map(entity, PhoneNumberDto.class);

    }

    public void activatePhoneNumber(String customerId, String phoneNumber) {
        Optional<CustomerEntity> customer = customerRepository.findByCustomerId(customerId);

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found for ID: " + customerId);
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            throw new InvalidPhoneNumberFormatException("Invalid phone number format: " + phoneNumber);
        }

        boolean phoneNumberExists = customer.get().getPhoneNumbers().stream()
                .anyMatch(p -> p.getPhoneNumber().equals(phoneNumber));
        if (phoneNumberExists) {
            System.out.println("Phone number already activated" +phoneNumber);
            throw new PhoneNumberAlreadyActivtedException("Phone number " + phoneNumber + " is already activated for customer ID: " + customerId);
        }

        PhoneNumberEntity phone = PhoneNumberEntity.builder()
                .customer(customer.get())
                .phoneNumber(phoneNumber)
                .build();
        System.out.println("saving phone is" +phone.getPhoneNumber());
        phoneNumberRepository.save(phone);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+?[0-9. ()-]{7,25}$"; // Example regex for a 10-digit phone number
        return Pattern.matches(regex, phoneNumber);
    }


    private void configureModelMapper() {
        if (modelMapper.getTypeMap(PhoneNumberEntity.class, PhoneNumberDto.class) == null) {
            modelMapper.createTypeMap(PhoneNumberEntity.class, PhoneNumberDto.class);
        }
    }


}


