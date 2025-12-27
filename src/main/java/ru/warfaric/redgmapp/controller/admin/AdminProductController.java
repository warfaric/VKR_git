package ru.warfaric.redgmapp.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.warfaric.redgmapp.dto.admin.ProductForm;
import ru.warfaric.redgmapp.service.AdminCatalogService;

@Controller
@RequestMapping("/admin/subcategories/{subcategoryId}/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminCatalogService adminCatalogService;

    @GetMapping
    public String list(@PathVariable Long subcategoryId, Model model) {
        var subcategory = adminCatalogService.getSubcategory(subcategoryId);
        model.addAttribute("subcategory", subcategory);
        model.addAttribute("category", subcategory.getCategory());
        model.addAttribute("products", adminCatalogService.listProducts(subcategoryId));
        return "admin/products/list";
    }

    @GetMapping("/new")
    public String createForm(@PathVariable Long subcategoryId, Model model) {
        ProductForm form = new ProductForm();
        form.setSubcategoryId(subcategoryId);
        var subcategory = adminCatalogService.getSubcategory(subcategoryId);
        model.addAttribute("productForm", form);
        model.addAttribute("subcategory", subcategory);
        model.addAttribute("category", subcategory.getCategory());
        return "admin/products/form";
    }

    @PostMapping
    public String create(@PathVariable Long subcategoryId,
                         @Valid ProductForm productForm,
                         BindingResult bindingResult,
                         Model model) {
        productForm.setSubcategoryId(subcategoryId);
        var subcategory = adminCatalogService.getSubcategory(subcategoryId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("productForm", productForm);
            model.addAttribute("subcategory", subcategory);
            model.addAttribute("category", subcategory.getCategory());
            return "admin/products/form";
        }
        adminCatalogService.createProduct(productForm);
        return "redirect:/admin/subcategories/" + subcategoryId + "/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long subcategoryId,
                           @PathVariable Long id,
                           Model model) {
        var product = adminCatalogService.getProduct(id);
        ProductForm form = new ProductForm();
        form.setId(product.getId());
        form.setName(product.getName());
        form.setPrice(product.getPrice());
        form.setWeight(product.getWeight());
        form.setSubcategoryId(product.getSubcategory().getId());
        var subcategory = adminCatalogService.getSubcategory(subcategoryId);
        model.addAttribute("productForm", form);
        model.addAttribute("subcategory", subcategory);
        model.addAttribute("category", subcategory.getCategory());
        return "admin/products/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long subcategoryId,
                         @PathVariable Long id,
                         @Valid ProductForm productForm,
                         BindingResult bindingResult,
                         Model model) {
        productForm.setSubcategoryId(subcategoryId);
        var subcategory = adminCatalogService.getSubcategory(subcategoryId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("productForm", productForm);
            model.addAttribute("subcategory", subcategory);
            model.addAttribute("category", subcategory.getCategory());
            return "admin/products/form";
        }
        adminCatalogService.updateProduct(id, productForm);
        return "redirect:/admin/subcategories/" + subcategoryId + "/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long subcategoryId, @PathVariable Long id) {
        adminCatalogService.deleteProduct(id);
        return "redirect:/admin/subcategories/" + subcategoryId + "/products";
    }
}
