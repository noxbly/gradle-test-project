package com.example.demo.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** jQuery Ajax가 호출하는 JSON API. */
@RestController
@RequestMapping("/api/todos")
public class TodoApiController {

    private final TodoService todoService;

    public TodoApiController(TodoService todoService) {
        this.todoService = todoService;
    }

    public record CreateTodoRequest(@NotBlank(message = "할 일을 입력하세요.")
                                    @Size(max = 100, message = "100자 이하로 입력하세요.") String title) {
    }

    @GetMapping
    public List<Todo> list() {
        return todoService.findAll();
    }

    @PostMapping
    public Todo create(@Valid @RequestBody CreateTodoRequest request) {
        return todoService.add(request.title());
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggle(@PathVariable long id) {
        return todoService.toggle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return todoService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
