package com.example.demo.web;

import java.time.LocalDateTime;

import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.todo.TodoService;

/** Thymeleaf로 서버 렌더링하는 페이지. */
@Controller
public class HomeController {

    private final TodoService todoService;

    public HomeController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("bootVersion", SpringBootVersion.getVersion());
        model.addAttribute("renderedAt", LocalDateTime.now());
        model.addAttribute("todos", todoService.findAll());
        return "index";
    }
}
