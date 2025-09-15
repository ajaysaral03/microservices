package microservices.microservices.controller;

import lombok.RequiredArgsConstructor;
import microservices.microservices.dto.ApiResponse;
import microservices.microservices.dto.ProductDto;
import microservices.microservices.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Create Product (File + JSON)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createProduct(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("data") ProductDto dto
    ) {
        ProductDto saved = productService.createProductWithFile(dto, file);
        return ResponseEntity.ok(new ApiResponse("Product created successfully", saved));
    }

    // Update Product (File + JSON)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable String id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("data") ProductDto dto
    ) {
        ProductDto updated = productService.updateProductWithFile(id, dto, file);
        return ResponseEntity.ok(new ApiResponse("Product updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    // ✅ Count Products
    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getProductsCount() {
        long count = productService.getProductsCount();
        return ResponseEntity.ok(new ApiResponse("Products count fetched successfully", count));
    }

}
