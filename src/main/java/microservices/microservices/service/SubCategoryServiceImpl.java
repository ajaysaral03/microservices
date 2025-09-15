package microservices.microservices.service;

import lombok.RequiredArgsConstructor;
import microservices.microservices.dto.SubCategoryDto;
import microservices.microservices.model.Category;
import microservices.microservices.model.SubCategory;
import microservices.microservices.repository.CategoryRepository;
import microservices.microservices.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public SubCategoryDto createSubCategory(SubCategoryDto dto) {
        SubCategory subCategory = toEntity(dto);
        return toDto(subCategoryRepository.save(subCategory));
    }

    @Override
    public SubCategoryDto updateSubCategory(String id, SubCategoryDto dto) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found with id: " + id));

        subCategory.setName(dto.getName());

        // Update category reference
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
        subCategory.setCategory(category);

        subCategory.setSlug(dto.getSlug());
        subCategory.setLocation(dto.getLocation());
        subCategory.setPage(dto.getPage());
        subCategory.setStatus(dto.getStatus());

        return toDto(subCategoryRepository.save(subCategory));
    }

    @Override
    public void deleteSubCategory(String id) {
        if (!subCategoryRepository.existsById(id)) {
            throw new RuntimeException("SubCategory not found with id: " + id);
        }
        subCategoryRepository.deleteById(id);
    }

    @Override
    public SubCategoryDto getSubCategory(String id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found with id: " + id));
        return toDto(subCategory);
    }

    @Override
    public List<SubCategoryDto> getAllSubCategories() {
        return subCategoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    // ✅ Count implementation
    @Override
    public long getSubCategoriesCount() {
        return subCategoryRepository.count();
    }
    // --------- Mapping Methods ----------
    private SubCategoryDto toDto(SubCategory entity) {
        return SubCategoryDto.builder()
                .id(entity.getId())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId().toString() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .name(entity.getName())
                .slug(entity.getSlug())
                .location(entity.getLocation())
                .page(entity.getPage())
                .status(entity.getStatus())
                .build();
    }

    private SubCategory toEntity(SubCategoryDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));

        return SubCategory.builder()
                .id(dto.getId())
                .category(category)  // ✅ map full Category object
                .name(dto.getName())
                .slug(dto.getSlug())
                .location(dto.getLocation())
                .page(dto.getPage())
                .status(dto.getStatus())
                .build();
    }
}
