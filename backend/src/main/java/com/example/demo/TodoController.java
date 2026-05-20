package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:5173") // 프런트엔드 포트 허용
public class TodoController {
    private final List<Todo> todos = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public TodoController() {
        // 초기 데이터
        todos.add(new Todo(counter.incrementAndGet(), "Spring Boot API 연결하기", false));
        todos.add(new Todo(counter.incrementAndGet(), "ArrayList로 메모리 DB 구현", true));
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todos;
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        todo.setId(counter.incrementAndGet());
        todos.add(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        for (Todo todo : todos) {
            if (todo.getId().equals(id)) {
                todo.setText(updatedTodo.getText());
                todo.setCompleted(updatedTodo.isCompleted());
                return todo;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todos.removeIf(todo -> todo.getId().equals(id));
    }
}
