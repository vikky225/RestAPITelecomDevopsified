package com.telecom.customer_contacts.service;

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
        System.out.println("customer is" + customer);
        return customer.map(c -> c.getPhoneNumbers().stream() // Assuming getPhoneNumbers() returns a List
                        .map(this::mapToDto) // Assuming mapToDto converts PhoneNumberEntity to PhoneNumberDto
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private PhoneNumberDto mapToDto(PhoneNumberEntity entity) {
        System.out.println("entity is" + entity.getPhoneNumber());
        PhoneNumberDto map = modelMapper.map(entity, PhoneNumberDto.class);
        System.out.println("amp is" + map.getPhoneNumber());
        return map;

    }

    public void activatePhoneNumber(String customerId, String phoneNumber) {
        Optional<CustomerEntity> customer = customerRepository.findByCustomerId(customerId);
        System.out.println("customer is" + customer.get().getCustomerId());
        customer.ifPresent(c -> {
            PhoneNumberEntity phone = PhoneNumberEntity.builder()
                    .customer(c)
                    .phoneNumber(phoneNumber)
                    .build();
            phoneNumberRepository.save(phone);
        });
    }


    private void configureModelMapper() {
        if (modelMapper.getTypeMap(PhoneNumberEntity.class, PhoneNumberDto.class) == null) {
            modelMapper.createTypeMap(PhoneNumberEntity.class, PhoneNumberDto.class);
        }
    }


}


