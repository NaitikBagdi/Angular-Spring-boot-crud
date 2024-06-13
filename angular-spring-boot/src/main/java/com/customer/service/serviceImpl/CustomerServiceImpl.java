package com.customer.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.customer.model.Customer;
import com.customer.model.CustomerDto;
import com.customer.repository.CustomerRepository;
import com.customer.service.CustomerService;

/**
 * Implementation of the {@link CustomerService} interface
 * Provides service methods for managing customers
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper = new ModelMapper();
	private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

	/**
	 * Constructs a new CustomerServiceImpl with the specified CustomerRepository
	 *
	 * @param customerRepository the repository used to manage customers
	 */
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	/**
	 * Retrieves all customers
	 *
	 * This method fetches all customer records from the database, converts them
	 * into CustomerDto objects, and returns them in a list
	 *
	 * @return a list of CustomerDto objects containing details of all customers
	 */
	@Override
	public List<CustomerDto> getAllCustomers() {
		List<Customer> customerList = customerRepository.findAll();
		return customerList.stream().map(customer -> modelMapper.map(customer, CustomerDto.class)).toList();
	}

	/**
	 * Saves a new customer
	 *
	 * This method checks for duplicate email and mobile number before saving a new customer
	 * If duplicates are found, it returns an appropriate error message
	 * Otherwise, it saves the customer and returns a success message
	 *
	 * @param customerDto the customer data transfer object containing customer details
	 * @return a ResponseEntity with a message indicating the result of the operation
	 */
	@Override
	public ResponseEntity<String> saveCustomer(CustomerDto customerDto) {
		ResponseEntity<String> response = null;
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
				Customer customer = convertToEntity(customerDto);
				customerRepository.save(customer);
				response = ResponseEntity.ok("Success");
			}
		} catch (Exception e) {
			logger.severe("Exception occurred while saving customer: " + e.getMessage());
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
		return response;
	}

	/**
	 * Deletes a customer by ID
	 *
	 * This method deletes a customer from the database using the provided customer ID
	 *
	 * @param id the ID of the customer to delete
	 */
	@Override
	public void deleteCustomer(Long id) {
		try {
			customerRepository.deleteById(id);
		} catch (Exception e) {
			logger.severe("Exception occurred while deleting customer: " + e.getMessage());
		}
	}

	/**
	 * Updates an existing customer
	 *
	 * This method updates the details of an existing customer by checking for
	 * duplicate email and mobile numbers.
	 * If no duplicates are found, it updates the customer record and returns a success message
	 * If duplicates are found, it returns an appropriate error message.
	 *
	 * @param id the ID of the customer to update
	 * @param customerDto the customer data transfer object containing updated customer details
	 * @return a ResponseEntity with a message indicating the result of the operation
	 */
	@Override
	public ResponseEntity<String> updateCustomer(Long id, CustomerDto customerDto) {
		try {
			Optional<Customer> customerOption = customerRepository.findById(id);

			boolean emailExisting = checkDuplicateEmail(id, customerDto.getEmail());
			boolean mobileExisting = checkDuplicateMobile(id, customerDto.getMobile());

			if (emailExisting && mobileExisting) {
				return ResponseEntity.badRequest().body("Both email and mobile number already exist");
			} else if (emailExisting) {
				return ResponseEntity.badRequest().body("Email already exists.");
			} else if (mobileExisting) {
				return ResponseEntity.badRequest().body("Mobile number already exists.");
			}

			return customerOption.map(existingCustomer -> {
				existingCustomer.setFirstName(customerDto.getFirstName());
				existingCustomer.setLastName(customerDto.getLastName());
				existingCustomer.setAge(customerDto.getAge());
				existingCustomer.setDob(customerDto.getDob());
				existingCustomer.setEmail(customerDto.getEmail());
				existingCustomer.setGender(customerDto.getGender());
				existingCustomer.setMobile(customerDto.getMobile());
				existingCustomer.setAddress1(customerDto.getAddress1());
				existingCustomer.setAddress2(customerDto.getAddress2());
				customerRepository.save(existingCustomer);
				CustomerDto updatedCustomerDto = convertToDto(existingCustomer);
				return ResponseEntity.ok("Success: " + updatedCustomerDto.toString());
			}).orElse(ResponseEntity.notFound().build());

		} catch (Exception e) {
			logger.severe("Exception occurred while updating customer: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

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

	/**
	 * Converts a CustomerDto to a Customer entity
	 *
	 * This method uses ModelMapper to convert a CustomerDto object to a Customer entity
	 *
	 * @param customerDto the data transfer object to convert
	 * @return the corresponding Customer entity
	 */
	private Customer convertToEntity(CustomerDto customerDto) {
		return modelMapper.map(customerDto, Customer.class);
	}

	/**
	 * Converts a Customer entity to a CustomerDto
	 *
	 * This method uses ModelMapper to convert a Customer entity to a CustomerDto object
	 *
	 * @param customer the entity to convert
	 * @return the corresponding CustomerDto
	 */
	private CustomerDto convertToDto(Customer customer) {
		return modelMapper.map(customer, CustomerDto.class);
	}

}