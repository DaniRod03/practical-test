package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll(Pageable pageable) {
		return repository.findAllByOrderByNameAsc();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}


	public Customer createCustomer(Customer customer){
		try{
			repository.save(customer);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public void updateCustomer(Customer customerEdit, Long id){
		Customer customer = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
		try{
			if(Objects.nonNull(customerEdit.getEmail()) && !customerEdit.getEmail().isEmpty()){
				customer.setEmail(customerEdit.getEmail());
				if(Objects.nonNull(customerEdit.getName()) && !customerEdit.getName().isEmpty()){
					customer.setName(customerEdit.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCustomer(Long id){
		Customer customerDelete = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
		repository.delete(customerDelete);
	}

}
