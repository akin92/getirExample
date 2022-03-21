package com.example.getir.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OrderRest implements Serializable {
    @JsonProperty(required = true)
    Integer customerId;

    @JsonProperty(required = true)
    Integer bookId;

    @JsonProperty(required = true)
    Integer itemCount;

    public OrderRest() {
    }

    public OrderRest(Integer customerId, Integer bookId) {
        this.customerId = customerId;
        this.bookId = bookId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}
