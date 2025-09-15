package microservices.microservices.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private String id;
    private String userId;
    private String userName;   // ✅ from User table
    private String userEmail;  // ✅ from User table
    private String orderNumber;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal shippingCharge;
    private String orderStatus;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
