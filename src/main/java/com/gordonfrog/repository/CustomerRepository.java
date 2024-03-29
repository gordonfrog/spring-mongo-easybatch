package com.gordonfrog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gordonfrog.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByCountry(String country);

}
