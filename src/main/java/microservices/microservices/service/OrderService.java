package microservices.microservices.service;

import microservices.microservices.dto.OrderRequestDto;
import microservices.microservices.dto.OrderResponseDto;
import microservices.microservices.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto dto);
    OrderResponseDto getOrderById(String id);
    List<OrderResponseDto> getOrdersByUser(String userId);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto updateOrderStatus(String orderId, OrderStatus status);
    void deleteOrder(String id);
}
