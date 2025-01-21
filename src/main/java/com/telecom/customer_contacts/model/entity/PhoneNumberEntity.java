package com.telecom.customer_contacts.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "phone_number_entity")
public class PhoneNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CustomerEntity customer;
        private String phoneNumber;

        public Builder customer(CustomerEntity customer) {
            this.customer = customer;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PhoneNumberEntity build() {
            PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
            phoneNumberEntity.customer = this.customer;
            phoneNumberEntity.phoneNumber = this.phoneNumber;
            return phoneNumberEntity;
        }
    }
}



