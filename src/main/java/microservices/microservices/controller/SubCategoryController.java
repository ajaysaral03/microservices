package microservices.microservices.controller;

import microservices.microservices.dto.SubCategoryDto;
import microservices.microservices.service.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@CrossOrigin(origins = "http://localhost:5173") // allow React frontend
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    // Create new subcategory
    @PostMapping
    public ResponseEntity<SubCategoryDto> create(@RequestBody SubCategoryDto dto) {
        SubCategoryDto created = subCategoryService.createSubCategory(dto);
        return ResponseEntity.ok(created);
    }

    // Update subcategory
    @PutMapping("/{id}")
    public ResponseEntity<SubCategoryDto> update(
            @PathVariable String id,
            @RequestBody SubCategoryDto dto
    ) {
        SubCategoryDto updated = subCategoryService.updateSubCategory(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete subcategory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Get single subcategory (with joined category info)
    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryDto> get(@PathVariable String id) {
        SubCategoryDto subCategory = subCategoryService.getSubCategory(id);
        return ResponseEntity.ok(subCategory);
    }

    // Get all subcategories (with category join)
    @GetMapping
    public ResponseEntity<List<SubCategoryDto>> getAll() {
        List<SubCategoryDto> subcategories = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(subcategories);
    }
}
