package com.example.getir.repository;

import com.example.getir.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o  where o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> getOrdersBetweenDates(Date startDate , Date endDate);

    @Query(value = "select to_char(order_Date,'MONTH') as month, count(o.*),sum(o.item_count) , sum(o.total_purchase) from orders o , customer c, book b where b.id=o.book_id and c.id =o.customer_id and c.id=2 group by month",nativeQuery = true)
    List<Object[]> getStatistic();
}
