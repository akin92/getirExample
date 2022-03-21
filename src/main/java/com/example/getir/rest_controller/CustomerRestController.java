package com.example.getir.rest_controller;

import com.example.getir.DtoModel.CustomerDto;
import com.example.getir.controller.CustomerController;
import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.Customer;
import com.example.getir.model.ResponseError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
    private static final Logger logger = LogManager.getLogger(CustomerRestController.class);

    @Autowired
    CustomerController customerController;

    @PostConstruct
    public void fillTables() {
        Customer customer1 = new Customer();
        customer1.setName("Akin");
        customer1.setSurname("Turgut");
        customer1.setAddress("Adress1");
        customer1.setEmail("akinturgut83@gmail.com");
        customer1.setPhone("5555555555");
        customer1.setPass("123456");
        try {
            customerController.registerCustomer(customer1);
        } catch (GetirLogicException cce) {
            logger.error(cce.getErrorCode() + ":" + cce.getMessage());
        }

    }

    @GetMapping("/customers")
    @ResponseBody
    public ResponseEntity<List<CustomerDto>> getCustomers(@RequestHeader Map<String, String> headers) {
        try {
            //Validate Token
            customerController.tokenValidation(headers);

            List<Customer> customerList = customerController.showAllCustomer();
            List<CustomerDto> customerDtoList = new ArrayList<>();
            customerList.forEach(e ->customerDtoList.add(new CustomerDto(e.getName(),e.getSurname(),e.getPhone(),e.getAddress(),e.getEmail())));

            if (customerList.isEmpty()) {
                logger.info("There are no customers in Db");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/customers/login/{email}/{pass}")
    @ResponseBody
    public ResponseEntity<String> getCustomerLogin(@RequestHeader Map<String, String> headers, @PathVariable String email, @PathVariable String pass) {
        try {
            String token = customerController.loginValidation(email, pass);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/customers")
    @ResponseBody
    public ResponseEntity<Customer> registerCustomer(@RequestHeader Map<String, String> headers, @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = new Customer();
            customer.setPass(customerDto.getPass());
            customer.setPhone(customerDto.getPhone());
            customer.setEmail(customerDto.getEmail());
            customer.setName(customerDto.getSurname());
            customer.setSurname(customerDto.getSurname());
            customer.setAddress(customerDto.getAddress());
            Customer registeredCustomer = customerController.registerCustomer(customer);
            return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
