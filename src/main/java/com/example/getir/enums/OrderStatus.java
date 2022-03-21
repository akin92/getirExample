package com.example.getir.enums;

public enum OrderStatus {
    ORDERED( "ORDERED"),
    ONWAY( "ON-WAY"),
    DELIVERED( "DELIVERED"),
    CANCELLED( "CANCELLED"),;



    private final String description;

    private OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return description;
    }
}
