package com.fawry.fawrymall.service.order;

import com.fawry.fawrymall.dto.OrderDto;
import com.fawry.fawrymall.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    Order findOrderById(Long id);
    Page<Order> findAllOrders(Pageable pageable);
    Order createOrder(OrderDto order);
    Order updateOrder(Long id, OrderDto order);
    void deleteOrder(Long id);
}
