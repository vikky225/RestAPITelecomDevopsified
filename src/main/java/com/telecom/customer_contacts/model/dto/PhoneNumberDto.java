package com.telecom.customer_contacts.model.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class PhoneNumberDto {

        @NotEmpty(message = "Phone number cannot be empty")
        @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
        private String phoneNumber;

        // No-argument constructor
        public PhoneNumberDto() {
        }

        // Constructor with phoneNumber parameter
        public PhoneNumberDto(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        // Getter
        public String getPhoneNumber() {
                return phoneNumber;
        }

        // Setter
        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }
}

