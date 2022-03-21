package com.example.getir.dao;

import com.example.getir.model.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getCustomerAll();

    Customer getCustomerById(Integer id);

    List<Customer> saveAll(List<Customer> customerList);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer existCustomerByEmailOrPhone(String email, String phone);

    Customer existCustomerByEmailAndPass(String email, String pass);
}
