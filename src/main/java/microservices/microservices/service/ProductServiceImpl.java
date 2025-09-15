package microservices.microservices.service;

import lombok.RequiredArgsConstructor;
import microservices.microservices.dto.ProductDto;
import microservices.microservices.model.Category;
import microservices.microservices.model.Product;
import microservices.microservices.model.SubCategory;
import microservices.microservices.repository.CategoryRepository;
import microservices.microservices.repository.ProductRepository;
import microservices.microservices.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    // Upload folder in project root
    private final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    // Convert Product entity to DTO
    private ProductDto toDto(Product product) {
        String categoryName = null;
        if (product.getCategoryId() != null) {
            categoryName = categoryRepository.findById(product.getCategoryId())
                    .map(Category::getName)
                    .orElse(null);
        }

        String subCategoryName = null;
        if (product.getSubCategoryId() != null) {
            subCategoryName = subCategoryRepository.findById(product.getSubCategoryId())
                    .map(SubCategory::getName)
                    .orElse(null);
        }

        return ProductDto.builder()
                .id(product.getId())
                .categoryId(product.getCategoryId())
                .categoryName(categoryName)
                .subCategoryId(product.getSubCategoryId())
                .subCategoryName(subCategoryName)
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice() != null ? product.getPrice() : 0.0)
                .stock(product.getStock() != null ? product.getStock() : 0)
                .image(product.getImage())
                .status(product.getStatus().name())
                .build();
    }

    // Convert DTO to Entity
    private Product toEntity(ProductDto dto) {
        return Product.builder()
                .categoryId(dto.getCategoryId())
                .subCategoryId(dto.getSubCategoryId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .image(dto.getImage())
                .status(dto.getStatus() != null && dto.getStatus().equalsIgnoreCase("inactive") ?
                        Product.Status.inactive : Product.Status.active)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // Create Product with File
    @Override
    public ProductDto createProductWithFile(ProductDto dto, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) uploadPath.mkdirs(); // ensure folder exists
            File dest = new File(uploadDir + fileName);
            try {
                file.transferTo(dest);
                dto.setImage("/uploads/" + fileName); // URL path
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }
        Product product = toEntity(dto);
        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    // Update Product with optional File
    @Override
    public ProductDto updateProductWithFile(String id, ProductDto dto, MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (dto.getCategoryId() != null) product.setCategoryId(dto.getCategoryId());
        if (dto.getSubCategoryId() != null) product.setSubCategoryId(dto.getSubCategoryId());
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        if (dto.getStatus() != null)
            product.setStatus(dto.getStatus().equalsIgnoreCase("inactive") ?
                    Product.Status.inactive : Product.Status.active);

        // File update
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) uploadPath.mkdirs();
            File dest = new File(uploadDir + fileName);
            try {
                file.transferTo(dest);
                product.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }

        product.setUpdatedAt(LocalDateTime.now());
        Product updated = productRepository.save(product);
        return toDto(updated);
    }

    @Override
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public long getProductsCount() {
        return productRepository.count();
    }
}
