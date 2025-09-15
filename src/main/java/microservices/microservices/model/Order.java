package microservices.microservices.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    private String id;

    private String userId;          // reference to User._id
    private String orderNumber;

    private BigDecimal totalAmount;
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal shippingCharge = BigDecimal.ZERO;

    private OrderStatus orderStatus = OrderStatus.PENDING;

    private String shippingAddress;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
