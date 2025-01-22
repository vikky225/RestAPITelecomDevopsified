package com.telecom.customer_contacts.controller;

import com.telecom.customer_contacts.exception.CustomerNotFoundException;
import com.telecom.customer_contacts.exception.InvalidPhoneNumberFormatException;
import com.telecom.customer_contacts.exception.PhoneNumberAlreadyActivtedException;
import com.telecom.customer_contacts.model.dto.PhoneNumberDto;
import com.telecom.customer_contacts.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("/phone-numbers")
    @Operation(summary = "Get all phone numbers", description = "Retrieves a list of all registered phone numbers.")
    @ApiResponse(responseCode = "200", description = "List of phone numbers",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PhoneNumberDto.class))))
    public List<PhoneNumberDto> getAllPhoneNumbers() {
        return phoneNumberService.getAllPhoneNumbers();
    }

    @GetMapping("/customers/{customerId}/phone-numbers")
    @Operation(summary = "Get phone numbers for a specific customer", description = "Retrieves a list of phone numbers associated with the given customer ID.",
            parameters = @Parameter(name = "customerId", description = "Customer ID", required = true))
    @ApiResponse(responseCode = "200", description = "List of phone numbers for the customer",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PhoneNumberDto.class))))
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<PhoneNumberDto>> getPhoneNumbersByCustomer(@PathVariable String customerId) {
        try {
            List<PhoneNumberDto> phoneNumbers = phoneNumberService.getPhoneNumbersByCustomer(customerId);
            return phoneNumbers.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(phoneNumbers);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/customers/{customerId}/phone-numbers")
    @Operation(summary = "Activate a phone number for a customer", description = "Activates the specified phone number for the given customer.",
            parameters = {@Parameter(name = "customerId", description = "Customer ID", required = true),
                    @Parameter(name = "phoneNumber", description = "Phone number to activate", required = true)})
    @ApiResponse(responseCode = "200", description = "Phone number activated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request or phone number not found")
    @ApiResponse(responseCode = "409", description = "Phone number already activated")
    public ResponseEntity<Void> activatePhoneNumber(@PathVariable String customerId, @Valid @RequestParam String phoneNumber) {
        try {
            phoneNumberService.activatePhoneNumber(customerId, phoneNumber);
            return ResponseEntity.ok().build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(404).build();
        } catch (PhoneNumberAlreadyActivtedException e) {
            return ResponseEntity.status(409).build();
        } catch (InvalidPhoneNumberFormatException e) {
            return ResponseEntity.status(400).build();
        }
    }


}