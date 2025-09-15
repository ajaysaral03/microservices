package microservices.microservices.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    private String id;

    private String orderId;   // reference to Order._id
    private String productId; // reference to Product._id

    private String productName;  // optional, for easier display
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice; // usually unitPrice * quantity

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

}
