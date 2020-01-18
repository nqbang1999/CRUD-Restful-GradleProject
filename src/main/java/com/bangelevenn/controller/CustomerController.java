package com.bangelevenn.controller;


import com.bangelevenn.model.Customer;
import com.bangelevenn.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController


public class CustomerController {
    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> listCustomers() {
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        System.out.println("Fetching Customer with id "+ id);
        Customer customer = customerService.findById(id);
        if (customer == null) {
            System.out.println("Customer with id "+id + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }


    @RequestMapping(value = "/create-customer", method = RequestMethod.POST)
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
        System.out.println("Create Customer "+ customer.getLastName());
        customerService.save(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/edit-customer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> editCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        System.out.println("Updating Customer "+ id);
        Customer currentCustomer = customerService.findById(id);
        if (currentCustomer == null) {
            System.out.println("Customer with id "+ id+ " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        currentCustomer.setFirstName(customer.getFirstName());
        currentCustomer.setLastName(customer.getLastName());
        currentCustomer.setId(customer.getId());

        customerService.save(currentCustomer);
        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-customer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            System.out.println("Unable to delete. Customer with id "+ id + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        customerService.remove(id);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }
}
