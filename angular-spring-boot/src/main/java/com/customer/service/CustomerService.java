package com.customer.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.customer.model.CustomerDto;

/**
 * Service interface for handling customer-related operations
 * 
 * @author Naitik Bagdi
 */
public interface CustomerService {

	/**
	 * Saves a new customer
	 *
	 * @param customerDto the customer data transfer object containing customer details
	 * @return a ResponseEntity with a message indicating the result of the operation
	 */
	ResponseEntity<String> saveCustomer(CustomerDto customerDto);

	/**
	 * Retrieves all customers
	 *
	 * @return a list of CustomerDto objects containing details of all customers
	 */
	List<CustomerDto> getAllCustomers();

	/**
	 * Updates an existing customer
	 *
	 * @param id the ID of the customer to update
	 * @param customerDto the customer data transfer object containing updated customer details
	 * @return a ResponseEntity with a message indicating the result of the operation
	 */
	ResponseEntity<String> updateCustomer(Long id, CustomerDto customerDto);

	/**
	 * Deletes a customer by ID
	 *
	 * @param id the ID of the customer to delete
	 */
	void deleteCustomer(Long id);

	/**
	 * Checks for duplicate mobile numbers, excluding a specific customer by ID
	 *
	 * @param id the ID of the customer to exclude from the check
	 * @param mobile the mobile number to check for duplicates
	 * @return true if a duplicate mobile number exists, false otherwise
	 */
	public boolean checkDuplicateMobile(Long id, String mobile);

	/**
	 * Checks for duplicate email addresses, excluding a specific customer by ID
	 *
	 * @param id the ID of the customer to exclude from the check
	 * @param email the email address to check for duplicates
	 * @return true if a duplicate email address exists, false otherwise
	 */
	public boolean checkDuplicateEmail(Long id, String email);

}