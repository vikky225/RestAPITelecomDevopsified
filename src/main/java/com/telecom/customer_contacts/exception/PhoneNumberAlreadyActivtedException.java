package com.telecom.customer_contacts.exception;

public class PhoneNumberAlreadyActivtedException extends RuntimeException{
    public PhoneNumberAlreadyActivtedException(String message) {
        super(message);
    }
}
