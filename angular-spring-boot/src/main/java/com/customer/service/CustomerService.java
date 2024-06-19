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
	 * This method checks for duplicate email and mobile number before saving a new
	 * customer If duplicates are found, it returns an appropriate error message
	 * Otherwise, it saves the customer and returns a success message
	 *
	 * @param customerDto the customer data transfer object containing customer details
	 * @return a ResponseEntity with a message indicating the result of the operation
	 */
	ResponseEntity<?> saveCustomer(CustomerDto customerDto);

	/**
	 * Retrieves all customers
	 *
	 * This method fetches all customer records from the database, converts them
	 * into CustomerDto objects, and returns them in a list
	 *
	 * @return a list of CustomerDto objects containing details of all customers
	 */
	List<CustomerDto> getAllCustomers();

	/**
	 * Deletes a customer by ID
	 *
	 * This method deletes a customer from the database using the provided customer
	 * ID
	 *
	 * @param id the ID of the customer to delete
	 */
	void deleteCustomer(Long id);

	/**
	 * Checks for duplicate mobile numbers, excluding a specific customer by ID
	 *
	 * This method checks if a customer with the given mobile number exists in the
	 * database, excluding the customer with the specified ID (if provided)
	 *
	 * @param id the ID of the customer to exclude from the check
	 * @param mobile the mobile number to check for duplicates
	 * @return true if a duplicate mobile number exists, false otherwise
	 */
	public boolean checkDuplicateMobile(Long id, String mobile);

	/**
	 * Checks for duplicate email addresses, excluding a specific customer by ID
	 *
	 * This method checks if a customer with the given email address exists in the
	 * database, excluding the customer with the specified ID (if provided)
	 *
	 * @param id the ID of the customer to exclude from the check
	 * @param email the email address to check for duplicates
	 * @return true if a duplicate email address exists, false otherwise
	 */
	public boolean checkDuplicateEmail(Long id, String email);

}