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
import ru.warfaric.redgmapp.dto.admin.CategoryForm;
import ru.warfaric.redgmapp.service.AdminCatalogService;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCatalogService adminCatalogService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", adminCatalogService.listCategories());
        return "admin/categories/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        return "admin/categories/form";
    }

    @PostMapping
    public String create(@Valid CategoryForm categoryForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryForm", categoryForm);
            return "admin/categories/form";
        }
        adminCatalogService.createCategory(categoryForm);
        return "redirect:/admin/categories";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var category = adminCatalogService.getCategory(id);
        CategoryForm form = new CategoryForm();
        form.setId(category.getId());
        form.setName(category.getName());
        model.addAttribute("categoryForm", form);
        return "admin/categories/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid CategoryForm categoryForm,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            categoryForm.setId(id);
            model.addAttribute("categoryForm", categoryForm);
            return "admin/categories/form";
        }
        adminCatalogService.updateCategory(id, categoryForm);
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        adminCatalogService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
}
