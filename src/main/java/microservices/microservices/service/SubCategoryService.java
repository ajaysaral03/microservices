package microservices.microservices.service;

import microservices.microservices.dto.SubCategoryDto;
import java.util.List;

public interface SubCategoryService {
    SubCategoryDto createSubCategory(SubCategoryDto dto);
    SubCategoryDto updateSubCategory(String id, SubCategoryDto dto);
    void deleteSubCategory(String id);
    SubCategoryDto getSubCategory(String id);
    List<SubCategoryDto> getAllSubCategories();
}
