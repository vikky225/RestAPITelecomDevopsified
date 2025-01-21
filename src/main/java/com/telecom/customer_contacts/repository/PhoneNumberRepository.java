package com.telecom.customer_contacts.repository;

import com.telecom.customer_contacts.model.entity.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity,Long> {

}
