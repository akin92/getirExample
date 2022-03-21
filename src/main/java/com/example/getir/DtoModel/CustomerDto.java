package com.example.getir.DtoModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;

public class CustomerDto implements Serializable {
    private String name;

    private String surname;

    private String phone;

    private String address;

    private String email;

    private String pass;

    public CustomerDto() {
    }

    public CustomerDto(String name, String surname, String phone, String address, String email, String pass) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.pass = pass;
    }

    public CustomerDto(String name, String surname, String phone, String address, String email) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.email = email;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
