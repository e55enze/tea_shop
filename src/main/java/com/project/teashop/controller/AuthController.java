package com.project.teashop.controller;

import com.project.teashop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/auth")
    public String showAuthPage(Model model) {
        return "auth";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        if (userService.authenticateUser(username, password).isPresent()) {
            session.setAttribute("username", username); // Сохраняем имя пользователя в сессии
            return "redirect:/"; // Успешный вход
        }
        model.addAttribute("error", "Неверное имя пользователя или пароль"); // Добавим сообщение об ошибке
        return "auth"; // Ошибка, вернуться на страницу авторизации
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        userService.registerUser(username, password); // Регистрация пользователя
        return "redirect:/auth"; // Успешная регистрация, возвращаем на auth
    }
}