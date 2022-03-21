package com.example.getir.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "customer")
//DTO kullanılabilir
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler"})
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column()
    private String name;

    @Column()
    private String surname;

    @Column(unique = true)
    private String phone;

    @Column()
    private String address;

    @Column(unique = true)
    private String email;

    @Column()
    private String pass;


    public Customer() {
        super();
    }

    public Customer(Integer id, String name, String surname, String phone, String address, String email, String pass) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.pass = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer customerId) {
        this.id = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}

