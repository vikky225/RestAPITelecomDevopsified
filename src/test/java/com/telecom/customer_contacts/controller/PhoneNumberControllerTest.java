
package com.telecom.customer_contacts.controller;

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

        mockMvc.perform(get("/api/phone-numbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testActivatePhoneNumber() throws Exception {
        String customerId = "1";
        String phoneNumber = "+1111111112";
        mockMvc.perform(post("/api/customers/{customerId}/phone-numbers",customerId)
                        .param("phoneNumber", phoneNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneNumbersByCustomer() throws Exception {
        String customerId = "1";
        mockMvc.perform(get("/api/customers/{customerId}/phone-numbers", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }




}