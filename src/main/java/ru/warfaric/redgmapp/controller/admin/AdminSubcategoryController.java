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
import ru.warfaric.redgmapp.dto.admin.SubcategoryForm;
import ru.warfaric.redgmapp.service.AdminCatalogService;

@Controller
@RequestMapping("/admin/categories/{categoryId}/subcategories")
@RequiredArgsConstructor
public class AdminSubcategoryController {

    private final AdminCatalogService adminCatalogService;

    @GetMapping
    public String list(@PathVariable Long categoryId, Model model) {
        model.addAttribute("category", adminCatalogService.getCategory(categoryId));
        model.addAttribute("subcategories", adminCatalogService.listSubcategories(categoryId));
        return "admin/subcategories/list";
    }

    @GetMapping("/new")
    public String createForm(@PathVariable Long categoryId, Model model) {
        SubcategoryForm form = new SubcategoryForm();
        form.setCategoryId(categoryId);
        model.addAttribute("subcategoryForm", form);
        model.addAttribute("category", adminCatalogService.getCategory(categoryId));
        return "admin/subcategories/form";
    }

    @PostMapping
    public String create(@PathVariable Long categoryId,
                         @Valid SubcategoryForm subcategoryForm,
                         BindingResult bindingResult,
                         Model model) {
        subcategoryForm.setCategoryId(categoryId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("subcategoryForm", subcategoryForm);
            model.addAttribute("category", adminCatalogService.getCategory(categoryId));
            return "admin/subcategories/form";
        }
        adminCatalogService.createSubcategory(subcategoryForm);
        return "redirect:/admin/categories/" + categoryId + "/subcategories";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long categoryId,
                           @PathVariable Long id,
                           Model model) {
        var subcategory = adminCatalogService.getSubcategory(id);
        SubcategoryForm form = new SubcategoryForm();
        form.setId(subcategory.getId());
        form.setName(subcategory.getName());
        form.setCategoryId(subcategory.getCategory().getId());
        model.addAttribute("subcategoryForm", form);
        model.addAttribute("category", adminCatalogService.getCategory(categoryId));
        return "admin/subcategories/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long categoryId,
                         @PathVariable Long id,
                         @Valid SubcategoryForm subcategoryForm,
                         BindingResult bindingResult,
                         Model model) {
        subcategoryForm.setCategoryId(categoryId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("subcategoryForm", subcategoryForm);
            model.addAttribute("category", adminCatalogService.getCategory(categoryId));
            return "admin/subcategories/form";
        }
        adminCatalogService.updateSubcategory(id, subcategoryForm);
        return "redirect:/admin/categories/" + categoryId + "/subcategories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long categoryId, @PathVariable Long id) {
        adminCatalogService.deleteSubcategory(id);
        return "redirect:/admin/categories/" + categoryId + "/subcategories";
    }
}
