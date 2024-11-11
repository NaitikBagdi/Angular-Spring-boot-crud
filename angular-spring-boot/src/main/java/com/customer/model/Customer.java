package com.customer.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a customer entity. Mapped to the "customer" table in the database
 * 
 * @author Naitik Bagdi
 */
@Setter
@Getter
@Entity
@Table(name = "customer")
public class Customer {

	/**
	 * The unique identifier for the customer. Automatically generated by the database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * The first name of the customer. Must not be null and has a maximum length of 30 characters
	 */
	@Column(name = "first_name", length = 30, nullable = false)
	private String firstName;

	/**
	 * The last name of the customer. Must not be null and has a maximum length of 30 characters
	 */
	@Column(name = "last_name", length = 30, nullable = false)
	private String lastName;

	/**
	 * The date of birth of the customer. Must not be null
	 */
	@Column(name = "date_of_birth", nullable = false)
	private Date dob;

	/**
	 * The mobile number of the customer. Must be unique, not null, and has a maximum length of 17 characters
	 */
	@Column(name = "mobile_number", length = 17, nullable = false, unique = true)
	private String mobile;

	/**
	 * The primary address of the customer. Must not be null and has a maximum length of 70 characters
	 */
	@Column(name = "address_one", length = 70, nullable = false)
	private String addressOne;

	/**
	 * The secondary address of the customer. Has a maximum length of 70 characters and can be null
	 */
	@Column(name = "address_two", length = 70)
	private String addressTwo;

	/**
	 * The age of the customer. Must not be null
	 */
	@Column(name = "age", nullable = false)
	private Integer age;

	/**
	 * The gender of the customer. Must not be null
	 * Represented as an integer where specific values correspond to specific genders
	 */
	@Column(name = "gender", nullable = false)
	private Integer gender;

	/**
	 * The email of the customer. Must be unique and not null
	 */
	@Column(name = "email", nullable = false, unique = true, length = 20)
	private String email;

}