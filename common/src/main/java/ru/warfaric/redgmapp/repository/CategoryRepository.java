package ru.warfaric.redgmapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.warfaric.redgmapp.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @EntityGraph(attributePaths = "subcategories")
    Optional<CategoryEntity> findWithSubcategoriesById(Long id);
}
