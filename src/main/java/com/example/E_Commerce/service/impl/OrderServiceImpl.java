package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Repository.AddressRepository;
import com.example.E_Commerce.Repository.OrderItemRepository;
import com.example.E_Commerce.Repository.OrderRepository;
import com.example.E_Commerce.domain.OrderStatus;
import com.example.E_Commerce.domain.PaymentStatus;
import com.example.E_Commerce.modal.*;
import com.example.E_Commerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
        if(!user.getAddress().contains(shippingAddress)) {
            user.getAddress().add(shippingAddress);
        }
        Address address = addressRepository.save(shippingAddress);
        Map<Long, List<CartItems>> itemsBySeller = cart.getCartItems().stream()
                .collect((Collectors.groupingBy(item -> item.getProduct().getSeller().getId())));
        Set<Order> orders = new HashSet<>();
        for (Map.Entry<Long, List<CartItems>> entry : itemsBySeller.entrySet()) {
            Long sellerId = entry.getKey();
            List<CartItems> cartItems = entry.getValue();
            int totalOrderPrice = cartItems.stream().mapToInt(CartItems::getSellingPrice).sum();
            int totalItem = cartItems.stream().mapToInt(CartItems::getQuantity).sum();
            Order createOrder = new Order();
            createOrder.setSellerId(sellerId);
            createOrder.setUser(user);
            createOrder.setTotalMRPPrice(totalOrderPrice);
            createOrder.setTotalItem(totalItem);
            createOrder.setShippingAddress(address);
            createOrder.setOrderStatus(OrderStatus.PENDING);
            createOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

            Order savedOrder = orderRepository.save(createOrder);
            orders.add(savedOrder);

            List<OrderItem> orderItems = new ArrayList<>();

            for(CartItems item: cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(item.getProduct());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSellingPrice(item.getSellingPrice());
                orderItem.setOrder(savedOrder);
                orderItem.setMRPPrice(item.getMRPPrice());
                orderItem.setSize(item.getSize());
                orderItem.setUserId(item.getUserId());

                savedOrder.getOrderItems().add(orderItem);

                OrderItem savedOrderItem = orderItemRepository.save(orderItem);
                orderItems.add(savedOrderItem);
            }
        }
        return orders;
    }

    @Override
    public Order findOrderById(long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(() -> new Exception("Order not found!"));
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> sellersOrder(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) throws Exception {
        Order order = findOrderById(orderId);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws Exception {
        Order order = findOrderById(orderId);
        if(!user.getId().equals(order.getUser().getId())) {
            throw new Exception("You don't have access to this order");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public OrderItem getOrderItemById(Long id) throws Exception {
        return orderItemRepository.findById(id).orElseThrow(() -> new Exception("Order item not exist"));
    }
}
