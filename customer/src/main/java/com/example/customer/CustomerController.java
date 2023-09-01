package com.example.customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public record CustomerController(CustomerService customerService) {
    private static final String CUSTOMERS = "/customers";
    @PostMapping(CUSTOMERS)
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        customerService.registerCustomer(customerRegistrationRequest);
        log.info("New customer registration request {} ", customerRegistrationRequest);
    }

    @GetMapping(CUSTOMERS)
    public List<Customer> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        log.info("All customers: {} ", customers);
        return customers;
    }
}
