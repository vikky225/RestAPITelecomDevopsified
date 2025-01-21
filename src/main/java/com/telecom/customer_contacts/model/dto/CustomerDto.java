package com.telecom.customer_contacts.model.dto;

import java.util.List;

public record CustomerDto(String customerId, String name, List<PhoneNumberDto> phoneNumbers)  {
}
