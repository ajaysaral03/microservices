    package microservices.microservices.dto;

    import lombok.*;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class SubCategoryDto {
        private String id;
        private String categoryId;
        private String categoryName; // NEW
        private String name;
        private String slug;
        private String location;
        private String page;
        private Integer status;
    }
