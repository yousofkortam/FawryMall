package com.fawry.fawrymall.controller;

import com.fawry.fawrymall.dto.OrderDto;
import com.fawry.fawrymall.entity.Order;
import com.fawry.fawrymall.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Page<Order> getAllOrders(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortingDirection
    ) {
        Sort sort;
        if (sortingDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(Sort.Direction.ASC, "date");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "date");
        }
        return orderService.findAllOrders(PageRequest.of(pageNo, size, sort));
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping
    public Order createOrder(@Valid @RequestBody OrderDto order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDto order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}
