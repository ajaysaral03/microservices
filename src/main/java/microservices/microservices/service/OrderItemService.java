package microservices.microservices.service;

import microservices.microservices.model.OrderItem;
import microservices.microservices.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> getItemsByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        orderItem.setTotalPrice(orderItem.getUnitPrice().multiply(
                new java.math.BigDecimal(orderItem.getQuantity())
        ));
        return orderItemRepository.save(orderItem);
    }

    public Optional<OrderItem> getOrderItemById(String id) {
        return orderItemRepository.findById(id);
    }

    public void deleteOrderItem(String id) {
        orderItemRepository.deleteById(id);
    }
}
