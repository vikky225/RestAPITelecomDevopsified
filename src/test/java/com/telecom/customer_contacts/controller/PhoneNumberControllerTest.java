
package com.telecom.customer_contacts.controller;

import com.telecom.customer_contacts.exception.CustomerNotFoundException;
import com.telecom.customer_contacts.exception.InvalidPhoneNumberFormatException;
import com.telecom.customer_contacts.exception.PhoneNumberAlreadyActivtedException;
import com.telecom.customer_contacts.service.PhoneNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PhoneNumberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PhoneNumberService phoneNumberService;

    @InjectMocks
    private PhoneNumberController phoneNumberController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(phoneNumberController).build();
    }

    @Test
    public void testGetAllPhoneNumbers() throws Exception {
        when(phoneNumberService.getAllPhoneNumbers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/phone-numbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testActivatePhoneNumber() throws Exception {
        mockMvc.perform(post("/phone-numbers/1")
                .param("phoneNumber", "+1234567890")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testActivatePhoneNumber_CustomerNotFound() throws Exception {
        String customerId = "1";
        String phoneNumber = "+1234567890";
        doThrow(new CustomerNotFoundException("Customer not found"))
                .when(phoneNumberService).activatePhoneNumber(customerId, phoneNumber);

        mockMvc.perform(post("/phone-numbers/{customerId}", customerId)
                        .param("phoneNumber", phoneNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testActivatePhoneNumber_InvalidPhoneNumberFormat() throws Exception {
        String customerId = "1";
        String phoneNumber = "invalid";

       doThrow(new InvalidPhoneNumberFormatException("Invalid phone number format")).when(phoneNumberService).activatePhoneNumber(customerId, phoneNumber);

        mockMvc.perform(post("/phone-numbers/{customerId}", customerId)
                        .param("phoneNumber", phoneNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testActivatePhoneNumber_PhoneNumberAlreadyActivated() throws Exception {
        String customerId = "1";
        String phoneNumber = "+1234567890";
         doThrow(new PhoneNumberAlreadyActivtedException("Phone number already activated")).when(phoneNumberService).activatePhoneNumber(customerId, phoneNumber);

        mockMvc.perform(post("/phone-numbers/{customerId}", customerId)
                        .param("phoneNumber", phoneNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }




}