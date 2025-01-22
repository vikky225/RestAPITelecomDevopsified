package com.telecom.customer_contacts.exception;

public class PhoneNumberNotFoundException extends RuntimeException{

    public PhoneNumberNotFoundException(String message) {
        super(message);
    }
}
