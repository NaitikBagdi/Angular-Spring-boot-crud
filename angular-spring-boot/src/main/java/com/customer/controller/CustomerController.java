package com.customer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.model.CustomerDto;
import com.customer.service.CustomerService;

import jakarta.validation.Valid;

/**
 * Controller class responsible for managing Customer-related operations This
 * class handles HTTP requests related to customers
 * 
 * @author Naitik Bagdi
 */
@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class CustomerController {

	private final CustomerService customerService;

	/**
	 * Constructs a CustomerController with the given CustomerService
	 * 
	 * @param customerService The service responsible for handling Customer operations
	 */
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Endpoint for saving a new customer.
	 * 
	 * @param customerDto The DTO representing the customer to be saved.
	 * @return ResponseEntity containing a success message or error status
	 * Returns HTTP 200 OK if successful, HTTP 400 BAD REQUEST if the request is
	 * invalid, or HTTP 404 NOT FOUND if the customer is not found.
	 */
	@PostMapping
	public ResponseEntity<String> saveCustomer(@Valid @RequestBody CustomerDto customerDto) {
		ResponseEntity<String> response = customerService.saveCustomer(customerDto);
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			return response; // Return the same response if there's a bad request
		} else if (response.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok("Customer saved successfully");
		} else {
			return ResponseEntity.notFound().build(); // Handle case where customer is not found
		}
	}

	/**
	 * Endpoint for retrieving all customers
	 * 
	 * @return List of CustomerDto representing all customers
	 */
	@GetMapping
	public List<CustomerDto> getCustomer() {
		return customerService.getAllCustomers();
	}

	/**
	 * Endpoint for updating an existing customer
	 * 
	 * @param id The ID of the customer to be updated
	 * @param customerDto The DTO representing the updated customer data
	 * @return ResponseEntity containing a success message or error status 
	 * Returns HTTP 200 OK if successful, HTTP 400 BAD REQUEST if the request is
	 * invalid, or HTTP 404 NOT FOUND if the customer is not found.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCustomer(@Valid @PathVariable Long id, @RequestBody CustomerDto customerDto) {
		ResponseEntity<String> response = customerService.updateCustomer(id, customerDto);
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			return response; // Return the same response if there's a bad request
		} else if (response.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok("Update successful.");
		} else {
			return ResponseEntity.notFound().build(); // Handle case where customer is not found
		}
	}

	/**
	 * Endpoint for deleting a customer
	 * 
	 * @param id The ID of the customer to be deleted
	 */
	@DeleteMapping("/{id}")
	public void deleteCustomer(@Valid @PathVariable Long id) {
		customerService.deleteCustomer(id);
	}

}