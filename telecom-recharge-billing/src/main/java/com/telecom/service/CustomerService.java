package com.telecom.service;

import com.telecom.model.Customer;
import com.telecom.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer) {

        return customerRepository.addCustomer(customer);
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.getAllCustomers();
    }

    public Customer getCustomerById(int id) {

        return customerRepository.getCustomerById(id);
    }
}