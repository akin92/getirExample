package com.example.getir.repository;

import com.example.getir.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmailOrPhone(String email,String phone);

    Customer findByEmailAndPass(String email,String pass);

    /*@Query(
            value = "SELECT * FROM Customer ORDER BY id \n-- #pageable\n",
            countQuery = "SELECT count(*) FROM Customer",
            nativeQuery = true)
    Page<Customer> findAllCustomersWithPagination(Pageable pageable);*/
}
