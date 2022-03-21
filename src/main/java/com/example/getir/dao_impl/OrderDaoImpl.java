package com.example.getir.dao_impl;

import com.example.getir.dao.OrderDao;
import com.example.getir.enums.ErrorCode;
import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.Order;
import com.example.getir.model.Statistic;
import com.example.getir.repository.OrderRepository;
import com.example.getir.rest_controller.CustomerRestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> getOrderAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> saveAll(List<Order> orderList) {
        return orderRepository.saveAll(orderList);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByDate(Date startDate, Date endDate) {
        return orderRepository.getOrdersBetweenDates(startDate, endDate);
    }

    @Override
    public List<Statistic> getStatistic() throws GetirLogicException {
        try {
            List<Statistic> statisticList = new ArrayList<>();
            List<Object[]> list = orderRepository.getStatistic();

            for (Object[] q1 : list) {

                String month = q1[0].toString();
                Integer totalOrdeCount = Integer.valueOf(q1[1].toString().trim());
                Integer totalBookCount = Integer.valueOf(q1[2].toString());
                Double totalPurchase = Double.valueOf(q1[3].toString());

                statisticList.add(new Statistic(month, totalOrdeCount, totalBookCount, totalPurchase));
            }
            return statisticList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GetirLogicException(ErrorCode.DATABASE);
        }
    }
}
