package com.telecom.customer_contacts.model.dto;

import java.util.List;

public class CustomerDto {
    private String customerId;
    private String name;
    private List<PhoneNumberDto> phoneNumbers;

    public CustomerDto() {
    }

    public CustomerDto(String customerId, String name, List<PhoneNumberDto> phoneNumbers) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneNumberDto> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumberDto> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}