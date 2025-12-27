package ru.warfaric.redgmapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.warfaric.redgmapp.service.CatalogService;

@RequiredArgsConstructor
@Controller
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", catalogService.getCatalog());
        return "catalog";
    }

    @GetMapping("/catalog/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        var category = catalogService.getCategoryWithSubcategories(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("subcategories", category.getSubcategories());
        return "subcategory";
    }

    @GetMapping("/catalog/{categoryId}/subcategory/{subcategoryId}")
    public String subcategory(@PathVariable Long categoryId,
                              @PathVariable Long subcategoryId,
                              Model model) {
        var subcategory = catalogService.getSubcategoryWithProducts(categoryId, subcategoryId);
        model.addAttribute("category", subcategory.getCategory());
        model.addAttribute("subcategory", subcategory);
        model.addAttribute("products", subcategory.getProducts());
        return "products";
    }
}
