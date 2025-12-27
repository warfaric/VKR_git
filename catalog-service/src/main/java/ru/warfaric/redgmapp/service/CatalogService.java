package ru.warfaric.redgmapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.warfaric.redgmapp.entity.CategoryEntity;
import ru.warfaric.redgmapp.entity.SubcategoryEntity;
import ru.warfaric.redgmapp.repository.CategoryRepository;
import ru.warfaric.redgmapp.repository.SubcategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryEntity> getCatalog() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Transactional(readOnly = true)
    public CategoryEntity getCategoryWithSubcategories(Long categoryId) {
        return categoryRepository.findWithSubcategoriesById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));
    }

    @Transactional(readOnly = true)
    public SubcategoryEntity getSubcategoryWithProducts(Long categoryId, Long subcategoryId) {
        SubcategoryEntity subcategory = subcategoryRepository.findWithProductsById(subcategoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Подкаталог не найден"));

        if (subcategory.getCategory() == null || !subcategory.getCategory().getId().equals(categoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Подкаталог не относится к категории");
        }

        return subcategory;
    }
}

