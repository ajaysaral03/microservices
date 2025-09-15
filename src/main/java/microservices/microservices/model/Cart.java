package microservices.microservices.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "carts")   // MongoDB collection name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    private String id;   // MongoDB ObjectId

    private String userId;     // Reference to users collection
    private String productId;  // Reference to products collection
    private int quantity;      // Default = 1
    private LocalDateTime createdAt;
}
