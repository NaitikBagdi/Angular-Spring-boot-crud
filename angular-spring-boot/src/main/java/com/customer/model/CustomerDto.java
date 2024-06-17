package com.customer.model;

import java.sql.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for customer information
 * Used to transfer data between processes in a more structured manner
 * 
 * @author Naitik Bagdi
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerDto {

	/**
	 * The unique identifier for the customer
	 */
	private long id;

	/**
	 * The first name of the customer. Must not be null and must be between 2 and 30 characters long
	 */
	@NotNull(message = "FirstName is required")
	@Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
	private String firstName;

	/**
	 * The last name of the customer. Must not be null and must be between 2 and 30 characters long
	 */
	@NotNull(message = "Last is required")
	@Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
	private String lastName;

	/**
	 * The date of birth of the customer. Must not be null
	 */
	@NotNull(message = "Date of birth is required")
	private Date dob;

	/**
	 * The mobile number of the customer. Must not be null and must be between 10 and 17 digits
	 */
	@NotNull(message = "Mobile number is required")
	@Pattern(regexp = "^\\d{10,17}$", message = "Mobile number should be 17 digits")
	private String mobile;

	/**
	 * The primary address of the customer. Must not be null and must be between 2 and 70 characters long
	 */
	@NotNull(message = "AddressOne number is required")
	@Size(min = 2, max = 70, message = "Address1 must be between 2 and 70 characters")
	private String addressOne;

	/**
	 * The secondary address of the customer. Can be null and must be between 2 and 70 characters long if provided.
	 */
	private String addressTwo;

	/**
	 * The age of the customer. Must not be null and must be between 1 and 99
	 */
	@NotNull(message = "Age number is required")
	@Min(value = 1, message = "Age must be at least two digits")
	@Max(value = 99, message = "Age must be at most two digits")
	private Integer age;

	/**
	 * The gender of the customer. Must not be null
	 */
	@NotNull(message = "Gender number is required")
	private Integer gender;

	/**
	 * The email of the customer. Must not be null and must match a valid email format
	 */
	@NotNull(message = "Email number is required")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Invalid email format")
	private String email;

}