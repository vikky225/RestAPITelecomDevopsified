
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
}