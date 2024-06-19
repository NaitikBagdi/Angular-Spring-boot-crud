package com.customer.util;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.customer.model.Customer;
import com.customer.model.CustomerDto;

@Service
public class CommonMethods {

	private final ModelMapper modelMapper = new ModelMapper();

	/**
	 * Converts a Customer entity to a CustomerDto
	 *
	 * This method uses ModelMapper to convert a Customer entity to a CustomerDto
	 * object
	 *
	 * @param customer the entity to convert
	 * @return the corresponding CustomerDto
	 */
	public CustomerDto convertToDto(Customer customer) {
		return modelMapper.map(customer, CustomerDto.class);
	}

	/**
	 * Converts a CustomerDto to a Customer entity
	 *
	 * This method uses ModelMapper to convert a CustomerDto object to a Customer
	 * entity
	 *
	 * @param customerDto the data transfer object to convert
	 * @return the corresponding Customer entity
	 */
	public Customer convertToEntity(CustomerDto customerDto) {
		return modelMapper.map(customerDto, Customer.class);
	}

	/**
	 * Converts a CustomerDto to a Customer entity
	 * Convert the list of Customer objects to a list of CustomerDto objects
	 * 
	 * @param customers
	 * @return the corresponding Customer list
	 */
	public List<CustomerDto> convertToDtoList(List<Customer> customers) {
		return customers.stream().map(this::convertToDto).toList();
	}
}
