package com.telecom.customer_contacts.controller;

import com.telecom.customer_contacts.CustomerContactsApplication;
import com.telecom.customer_contacts.service.PhoneNumberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CustomerContactsApplication.class)
@ActiveProfiles("test")
@Transactional
public class PhoneNumberControllerIntegrationTest {


    @Autowired
    private PhoneNumberService phoneNumberService;

    private MockMvc mockMvc;

    @Test
    public void testGetAllPhoneNumbers() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new PhoneNumberController(phoneNumberService)).build();

        mockMvc.perform(get("/phone-numbers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testActivatePhoneNumber() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new PhoneNumberController(phoneNumberService)).build();

        mockMvc.perform(post("/phone-numbers/1")
                        .param("phoneNumber", "+1234567891")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
