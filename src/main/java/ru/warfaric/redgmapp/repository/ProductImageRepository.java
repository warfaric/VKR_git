package ru.warfaric.redgmapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warfaric.redgmapp.entity.ProductImageEntity;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findAllByProductIdOrderBySortOrderAsc(Long productId);
}

