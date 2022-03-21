package com.example.getir.dao;



import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.Order;
import com.example.getir.model.Statistic;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    List<Order> getOrderAll();

    Order getOrderById(Integer id);

    List<Order> saveAll(List<Order> customerList);

    Order saveOrder(Order customer);

    Order updateOrder(Order customer);

    List<Order>getOrdersByDate(Date startDate , Date endDate);

    List<Statistic>getStatistic() throws GetirLogicException;
}
