package microservices.microservices.controller;

import jakarta.validation.Valid;
import microservices.microservices.dto.ApiResponse;
import microservices.microservices.dto.OrderRequestDto;
import microservices.microservices.dto.OrderResponseDto;
import microservices.microservices.model.OrderStatus;
import microservices.microservices.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @Valid @RequestBody OrderRequestDto request) {
        return ResponseEntity.ok(
                ApiResponse.success("Order created", orderService.createOrder(request))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrder(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.success("Fetched order", orderService.getOrderById(id))
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getOrdersByUser(@PathVariable String userId) {
        return ResponseEntity.ok(
                ApiResponse.success("Orders by user", orderService.getOrdersByUser(userId))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getAllOrders() {
        return ResponseEntity.ok(
                ApiResponse.success("All orders", orderService.getAllOrders())
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateStatus(
            @PathVariable String id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(
                ApiResponse.success("Status updated", orderService.updateOrderStatus(id, status))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Order deleted"));
    }
}
