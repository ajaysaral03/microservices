package microservices.microservices.dto;

import lombok.Data;
import microservices.microservices.model.OrderStatus;

import java.math.BigDecimal;

@Data
public class OrderRequestDto {
    private String userId;
    private String orderNumber;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal shippingCharge;
    private OrderStatus orderStatus;
    private String shippingAddress;
}
