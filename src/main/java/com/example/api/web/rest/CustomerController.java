package com.example.api.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public List<Customer> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@PostMapping("/new")
	public ResponseEntity<Object> createCustomer(@RequestBody Customer customerRegister){
		service.createCustomer(customerRegister);
		ResponseEntity<Object>  body = ResponseEntity.status(HttpStatus.CREATED).body(customerRegister);
		return body;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCustomer(@RequestBody Customer customerEdit, @PathVariable Long id){
		service.updateCustomer(customerEdit, id);
		ResponseEntity<Object> build = ResponseEntity.noContent().build();
		return build;
	}

	@DeleteMapping("/{id")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long id){
		service.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}

}
