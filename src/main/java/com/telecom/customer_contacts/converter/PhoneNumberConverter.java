package com.telecom.customer_contacts.converter;

import com.telecom.customer_contacts.model.dto.PhoneNumberDto;
import com.telecom.customer_contacts.model.entity.PhoneNumberEntity;

public class PhoneNumberConverter {

    public static PhoneNumberDto toDto(PhoneNumberEntity phoneNumberEntity) {
        return new PhoneNumberDto(phoneNumberEntity.getPhoneNumber());
    }

    public static PhoneNumberEntity toEntity(PhoneNumberDto phoneNumberDto) {
        PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
        phoneNumberEntity.setPhoneNumber(phoneNumberDto.getPhoneNumber());
        return phoneNumberEntity;
    }
}