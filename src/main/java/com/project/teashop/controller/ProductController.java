package com.project.teashop.controller;

import com.project.teashop.entity.Category;
import com.project.teashop.entity.Product;
import com.project.teashop.entity.TypePackaging;
import com.project.teashop.repository.CategoryRepos;
import com.project.teashop.repository.ProductRepos;
import com.project.teashop.repository.TypePackagingRepos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductRepos productRepos;
    private final CategoryRepos categoryRepos;
    private final TypePackagingRepos typePackagingRepos;

    public ProductController(ProductRepos productRepos,
                             CategoryRepos categoryRepos,
                             TypePackagingRepos typePackagingRepos
                             ) {

        this.productRepos = productRepos;
        this.categoryRepos = categoryRepos;
        this.typePackagingRepos = typePackagingRepos;
    }

    @GetMapping("/")
    public String listProducts(Model model) {
//        HttpSession session = request.getSession(false); // Получаем текущую сессию, если она существует
//        boolean isAuthenticated = session != null && session.getAttribute("user") != null; // Проверяем, авторизован ли пользователь
//
//        if (!isAuthenticated) {
////            return "redirect:/auth"; // Если не авторизован, перенаправляем на страницу авторизации
//        }

        List<Product> products = (List<Product>) productRepos.findAll();
        System.out.println(products);
        List<Category> categories = categoryRepos.findAll();
        for(Category category : categories) {
            System.out.println("Category ID: " + category.getId() + ", Name: " + category.getCategory());
        }
        List<TypePackaging> typePackagings = typePackagingRepos.findAll();
        System.out.println("categories: " + categories);
        System.out.println("typePackagings: " + typePackagings);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("type_packaging", typePackagings);
        return "main";
    }
}
