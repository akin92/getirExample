package com.example.getir.dao_impl;

import com.example.getir.dao.CustomerDao;
import com.example.getir.model.Customer;
import com.example.getir.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Transactional
@Service
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public List<Customer> saveAll(List<Customer> customerList) {
        return customerRepository.saveAll(customerList);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer existCustomerByEmailOrPhone(String email, String phone) {
        return customerRepository.findByEmailOrPhone(email, phone);
    }

    @Override
    public Customer existCustomerByEmailAndPass(String email, String pass) {
        return customerRepository.findByEmailAndPass(email, pass);
    }
}
