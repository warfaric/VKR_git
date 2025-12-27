package ru.warfaric.redgmapp.dto.admin;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public class ProductForm {

    private Long id;

    @NotBlank(message = "Название обязательно")
    private String name;

    @NumberFormat(pattern = "#0.###")
    @DecimalMin(value = "0.0", inclusive = false, message = "Вес должен быть положительным")
    private BigDecimal weight;

    @NotNull(message = "Цена обязательна")
    @NumberFormat(pattern = "#0.##")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть положительной")
    private BigDecimal price;

    @NotNull
    private Long subcategoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }
}
