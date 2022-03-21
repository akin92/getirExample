package com.example.getir.controller;

import com.example.getir.dao_impl.BookDaoImpl;
import com.example.getir.dao_impl.CustomerDaoImpl;
import com.example.getir.dao_impl.OrderDaoImpl;
import com.example.getir.enums.ErrorCode;
import com.example.getir.enums.OrderStatus;
import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.Book;
import com.example.getir.model.Customer;
import com.example.getir.model.Order;
import com.example.getir.model.Statistic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    /*Will persist new order (statuses may used)
      Will update stock records.
      (Hint: what if it happens if 2 or more users tries to buy one last book at the same time)
      Will query order by Id
      List orders by date interval ( startDate - endDate )*/

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    OrderDaoImpl orderDaoImpl;

    @Autowired
    CustomerDaoImpl customerDaoImpl;

    @Autowired
    BookDaoImpl bookDaoImpl;

    public Order showOrderById(Integer orderId) throws GetirLogicException {
        try {
            return orderDaoImpl.getOrderById(orderId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GetirLogicException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    public List<Order> showOrdersByDate(Date startDate, Date endDate) throws GetirLogicException {
        try {
            return orderDaoImpl.getOrdersByDate(startDate, endDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GetirLogicException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    public Order createNewOrder(Integer customerId , Integer bookId , Integer itemCount) throws GetirLogicException {
        try {
            Customer customer = new Customer();
            customer = customerDaoImpl.getCustomerById(customerId);
            if(customer == null){
                throw  new GetirLogicException(ErrorCode.USER_NOT_EXISTS);
            }
            if(bookDaoImpl.decraseBookStock(bookId, itemCount) == 1){
                Book book = new Book();
                book = bookDaoImpl.getBookById(bookId);
                Order order = new Order();
                order.setOrderDate(new Date());
                order.setCustomer(customer);
                order.setStatus(OrderStatus.ORDERED.getDescription());
                order.setBook(book);
                order.setItemCount(itemCount);
                order.setTotalPurchase(book.getPrice() * itemCount);
               return orderDaoImpl.saveOrder(order);
            }else{
                return null;
            }
        }catch (GetirLogicException e){
            throw  new GetirLogicException(e.getErrorCode().getDescription(),e.getErrorCode());
        }
    }
    public List<Statistic> showStatistics() throws GetirLogicException {
        try {
            return orderDaoImpl.getStatistic();
        }catch (GetirLogicException gle){
            throw  new GetirLogicException(gle.getErrorCode().getDescription(),gle.getErrorCode());
        }
    }
}
