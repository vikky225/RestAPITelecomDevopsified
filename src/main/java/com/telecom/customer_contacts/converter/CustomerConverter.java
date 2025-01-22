package com.telecom.customer_contacts.converter;

import com.telecom.customer_contacts.model.dto.CustomerDto;
import com.telecom.customer_contacts.model.dto.PhoneNumberDto;
import com.telecom.customer_contacts.model.entity.CustomerEntity;
import com.telecom.customer_contacts.model.entity.PhoneNumberEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerConverter {

    public static CustomerDto toDto(CustomerEntity customerEntity) {
        List<PhoneNumberDto> phoneNumbers = customerEntity.getPhoneNumbers().stream()
                .map(phoneNumberEntity -> new PhoneNumberDto(phoneNumberEntity.getPhoneNumber()))
                .collect(Collectors.toList());

        return new CustomerDto(customerEntity.getCustomerId(), customerEntity.getName(), phoneNumbers);
    }

    public static CustomerEntity toEntity(CustomerDto customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(customerDto.getCustomerId());
        customerEntity.setName(customerDto.getName());

        List<PhoneNumberEntity> phoneNumbers = customerDto.getPhoneNumbers().stream()
                .map(phoneNumberDto -> {
                    PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
                    phoneNumberEntity.setPhoneNumber(phoneNumberDto.getPhoneNumber());
                    phoneNumberEntity.setCustomer(customerEntity);
                    return phoneNumberEntity;
                })
                .collect(Collectors.toList());

        customerEntity.setPhoneNumbers(phoneNumbers);
        return customerEntity;
    }
}