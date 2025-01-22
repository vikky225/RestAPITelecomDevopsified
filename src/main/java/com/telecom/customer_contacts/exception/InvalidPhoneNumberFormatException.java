package com.telecom.customer_contacts.exception;

public class InvalidPhoneNumberFormatException extends RuntimeException {
    public InvalidPhoneNumberFormatException(String message) {
        super(message);
    }
}
