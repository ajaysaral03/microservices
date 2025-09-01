package microservices.microservices.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    private String name;

    private String slug;

    private Long parentId;   // सिर्फ parent का id देंगे

    private Integer status;
}
