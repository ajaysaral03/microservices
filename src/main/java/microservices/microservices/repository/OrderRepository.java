package microservices.microservices.repository;

import microservices.microservices.model.Order;
import microservices.microservices.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByUserId(String userId);
    List<Order> findByOrderStatus(OrderStatus status);
}
