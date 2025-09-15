package microservices.microservices.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String id;
    private String categoryId;
    private String categoryName;
    private String subCategoryId;
    private String subCategoryName;
    private String name;
    private String description;
    private Double price;   // null support ke liye Double
    private Integer stock;  // null support ke liye Integer
    private String image;
    private String status;
}
