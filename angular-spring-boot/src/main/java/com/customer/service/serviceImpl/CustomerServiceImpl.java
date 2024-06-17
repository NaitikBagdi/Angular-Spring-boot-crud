package com.customer.service.serviceImpl;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.customer.model.Customer;
import com.customer.model.CustomerDto;
import com.customer.repository.CustomerRepository;
import com.customer.service.CustomerService;
import com.customer.util.CommonMethods;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the {@link CustomerService} interface Provides service
 * methods for managing customers
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CommonMethods commonMethods;
	private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

	// Retrieves all customers as DTOs
	@Override
	public List<CustomerDto> getAllCustomers() {
		List<Customer> customerList = customerRepository.findAll();
		return commonMethods.convertToDtoList(customerList);
	}

	/**
	 * Saves a new customer or updates an existing one if ID is provided
	 * Checks for duplicate email and mobile number before saving
	 */
	@Override
	public ResponseEntity<?> saveCustomer(CustomerDto customerDto) {
		ResponseEntity<?> response = null;
		try {
			boolean isEmailDuplicate = checkDuplicateEmail(customerDto.getId(), customerDto.getEmail());
			boolean isMobileDuplicate = checkDuplicateMobile(customerDto.getId(), customerDto.getMobile());

			if (isEmailDuplicate && isMobileDuplicate) {
				response = ResponseEntity.badRequest().body("Both email and mobile number already exist");
			} else if (isEmailDuplicate) {
				response = ResponseEntity.badRequest().body("Email already exists.");
			} else if (isMobileDuplicate) {
				response = ResponseEntity.badRequest().body("Mobile number already exists.");
			} else {
				Customer customer = commonMethods.convertToEntity(customerDto);
				customerRepository.save(customer);
				response = ResponseEntity.ok(customer);
			}
		} catch (Exception e) {
			logger.severe("Exception occurred while saving customer: " + e.getMessage());
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
		return response;
	}


	// Deletes a customer by ID
	@Override
	public void deleteCustomer(Long id) {
		try {
			customerRepository.deleteById(id);
		} catch (Exception e) {
			logger.severe("Exception occurred while deleting customer: " + e.getMessage());
		}
	}


	// Checks if the provided email already exists in the database, excluding the given ID
	@Override
	public boolean checkDuplicateEmail(Long id, String email) {
		boolean duplicateEmail = false;
		try {
			if (id != null && id > 0) {
				duplicateEmail = customerRepository.existsByEmailAndIdNot(email, id);
			} else {
				duplicateEmail = customerRepository.existsByEmail(email);
			}
		} catch (Exception e) {
			logger.severe("Exception occurred while checking duplicate email: " + e.getMessage());
		}
		return duplicateEmail;
	}


	// Checks if the provided mobile number already exists in the database, excluding the given ID
	@Override
	public boolean checkDuplicateMobile(Long id, String mobile) {
		boolean duplicateMobile = false;
		try {
			if (id != null && id > 0) {
				duplicateMobile = customerRepository.existsByMobileAndIdNot(mobile, id);
			} else {
				duplicateMobile = customerRepository.existsByMobile(mobile);
			}
		} catch (Exception e) {
			logger.severe("Exception occurred while checking duplicate mobile: " + e.getMessage());
		}
		return duplicateMobile;
	}

}