package microservices.microservices.service;

import microservices.microservices.dto.OrderRequestDto;
import microservices.microservices.dto.OrderResponseDto;
import microservices.microservices.model.Order;
import microservices.microservices.model.OrderStatus;
import microservices.microservices.repository.OrderRepository;
import microservices.microservices.repository.UserRepository;
import microservices.microservices.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // ✅ Create Order
    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order order = Order.builder()
                .userId(dto.getUserId())
                .orderNumber(dto.getOrderNumber())
                .totalAmount(dto.getTotalAmount() != null ? dto.getTotalAmount() : BigDecimal.ZERO)
                .discount(dto.getDiscount() != null ? dto.getDiscount() : BigDecimal.ZERO)
                .shippingCharge(dto.getShippingCharge() != null ? dto.getShippingCharge() : BigDecimal.ZERO)
                .orderStatus(dto.getOrderStatus() != null ? dto.getOrderStatus() : OrderStatus.PENDING)
                .shippingAddress(dto.getShippingAddress())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }

    // ✅ Get Order By Id
    @Override
    public OrderResponseDto getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToDto(order);
    }

    // ✅ Get Orders By User
    @Override
    public List<OrderResponseDto> getOrdersByUser(String userId) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getUserId().equals(userId))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ✅ Get All Orders
    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ✅ Update Status
    @Override
    public OrderResponseDto updateOrderStatus(String orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        return mapToDto(orderRepository.save(order));
    }

    // ✅ Delete Order
    @Override
    public void deleteOrder(String id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    // ✅ Helper: Map Order entity to OrderResponseDto
    private OrderResponseDto mapToDto(Order order) {
        String userName = null;
        String userEmail = null;

        if (order.getUserId() != null) {
            var userOpt = userRepository.findById(order.getUserId());
            if (userOpt.isPresent()) {
                userName = userOpt.get().getName();
                userEmail = userOpt.get().getEmail();
            }
        }

        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .userName(userName)     // ✅ from User
                .userEmail(userEmail)   // ✅ from User
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .discount(order.getDiscount())
                .shippingCharge(order.getShippingCharge())
                .orderStatus(order.getOrderStatus().name())
                .shippingAddress(order.getShippingAddress())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
