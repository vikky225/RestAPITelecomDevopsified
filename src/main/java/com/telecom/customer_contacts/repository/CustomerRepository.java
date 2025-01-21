package com.telecom.customer_contacts.repository;

import com.telecom.customer_contacts.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    Optional<CustomerEntity> findByCustomerId(String customerId);
}
