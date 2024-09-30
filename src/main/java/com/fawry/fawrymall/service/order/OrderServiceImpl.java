package com.fawry.fawrymall.service.order;

import com.fawry.fawrymall.dto.OrderDto;
import com.fawry.fawrymall.dto.OrderItemDto;
import com.fawry.fawrymall.entity.Merchant;
import com.fawry.fawrymall.entity.Order;
import com.fawry.fawrymall.entity.OrderItem;
import com.fawry.fawrymall.entity.Product;
import com.fawry.fawrymall.enums.OrderStatus;
import com.fawry.fawrymall.repository.OrderRepository;
import com.fawry.fawrymall.service.mechant.MerchantService;
import com.fawry.fawrymall.service.product.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MerchantService merchantService;
    private final ProductService productService;

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order with id " + id + " not found")
        );
    }

    @Override
    public Page<Order> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Order createOrder(OrderDto order) {
        Merchant customer = merchantService.getMerchantById(order.customerId());
        Order newOrder = new Order();

        List<OrderItem> orderItems = new LinkedList<>();
        newOrder.setMerchant(customer);
        newOrder.setDate(LocalDateTime.now());
        newOrder.setStatus(OrderStatus.PENDING);

        double totalAmount = 0;
        for (OrderItemDto item : order.items()) {
            Product product = productService.getProductById(item.productId());
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(item.quantity())
                    .unitPrice(product.getPrice())
                    .totalPrice(product.getPrice() * item.quantity())
                    .order(newOrder)
                    .build();
            totalAmount += orderItem.getTotalPrice();
            orderItems.add(orderItem);
        }
        newOrder.setItems(orderItems);
        newOrder.setTotalPrice(totalAmount);
        return orderRepository.save(newOrder);
    }

    @Override
    public Order updateOrder(Long id, OrderDto order) {
        // TODO implement update order logic
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = findOrderById(id);
        orderRepository.delete(order);
    }
}
