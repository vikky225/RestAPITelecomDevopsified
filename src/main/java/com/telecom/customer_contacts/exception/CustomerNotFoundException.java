package com.telecom.customer_contacts.exception;

public class CustomerNotFoundException extends RuntimeException {

        public CustomerNotFoundException(String message) {
            super(message);
        }
}
