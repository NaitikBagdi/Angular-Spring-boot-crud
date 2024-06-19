package com.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.model.Customer;

/**
 * Repository interface for {@link Customer} entities
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods for Customer entities
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * Checks if a customer with the given email exists
	 *
	 * @param email the email to check for existence
	 * @return true if a customer with the given email exists, false otherwise
	 */
	boolean existsByEmail(String email);

	/**
	 * Checks if a customer with the given mobile number exists
	 *
	 * @param mobile the mobile number to check for existence
	 * @return true if a customer with the given mobile number exists, false otherwise
	 */
	boolean existsByMobile(String mobile);

	/**
	 * Checks if a customer with the given email exists, excluding a specific customer by ID
	 *
	 * @param email the email to check for existence
	 * @param id the ID of the customer to exclude from the check
	 * @return true if a customer with the given email exists (excluding the specified ID), false otherwise
	 */
	boolean existsByEmailAndIdNot(String email, long id);

	/**
	 * Checks if a customer with the given mobile number exists, excluding a specific customer by ID
	 *
	 * @param mobile the mobile number to check for existence
	 * @param id the ID of the customer to exclude from the check
	 * @return true if a customer with the given mobile number exists (excluding the specified ID), false otherwise
	 */
	boolean existsByMobileAndIdNot(String mobile, long id);

}