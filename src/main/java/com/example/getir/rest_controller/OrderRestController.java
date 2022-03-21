package com.example.getir.rest_controller;

import com.example.getir.controller.CustomerController;
import com.example.getir.controller.OrderController;
import com.example.getir.dao_impl.BookDaoImpl;
import com.example.getir.dao_impl.CustomerDaoImpl;
import com.example.getir.dao_impl.OrderDaoImpl;
import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderRestController {
    private static final Logger logger = LogManager.getLogger(BookRestController.class);

    @Autowired
    OrderController orderController;

    @Autowired
    CustomerController customerController;

    @GetMapping("/orders/{orderId}")
    @ResponseBody
    public ResponseEntity<Order> getOrderById(@RequestHeader Map<String, String> headers, @PathVariable Integer orderId) {
        try {
            customerController.tokenValidation(headers);
            Order order = orderController.showOrderById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{startDate}/{endDate}")
    @ResponseBody
    public ResponseEntity<List<Order>> getOrderById(@RequestHeader Map<String, String> headers, @PathVariable String startDate , @PathVariable String endDate) {
        try {
            List<Order> orderList = new ArrayList<>();
            customerController.tokenValidation(headers);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date dBegin = sdf.parse(startDate);
            Date dEnd = sdf.parse(endDate);
            orderList = orderController.showOrdersByDate(dBegin,dEnd);
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orders")
    @ResponseBody
    public ResponseEntity<Order> registerCustomer(@RequestHeader Map<String, String> headers, @RequestBody OrderRest orderRest) {
        try {
            customerController.tokenValidation(headers);
            Order responseOrder = new Order();
            responseOrder = orderController.createNewOrder(orderRest.getCustomerId(), orderRest.getBookId(),orderRest.getItemCount());
            return new ResponseEntity<>(responseOrder, HttpStatus.OK);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/orders/statistic")
    @ResponseBody
    public ResponseEntity<List<Statistic>> getStatistics(@RequestHeader Map<String, String> headers) {
        try {
            //customerController.tokenValidation(headers);
            List<Statistic> statisticList = orderController.showStatistics();
            return new ResponseEntity<>(statisticList, HttpStatus.OK);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
