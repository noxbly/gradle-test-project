package com.example.demo.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

/** 메모리 저장소 기반 할 일 서비스 (서버를 재시작하면 초기화된다). */
@Service
public class TodoService {

    private final Map<Long, Todo> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public TodoService() {
        add("Spring Boot 3.5.2 프로젝트 실행하기");
        add("Thymeleaf로 서버 렌더링 확인하기");
        add("jQuery Ajax로 할 일 추가해 보기");
    }

    public List<Todo> findAll() {
        List<Todo> list = new ArrayList<>(store.values());
        list.sort((a, b) -> Long.compare(a.id(), b.id()));
        return list;
    }

    public Todo add(String title) {
        long id = sequence.incrementAndGet();
        Todo todo = new Todo(id, title.trim(), false);
        store.put(id, todo);
        return todo;
    }

    public Optional<Todo> toggle(long id) {
        return Optional.ofNullable(store.computeIfPresent(id, (key, todo) -> todo.withDone(!todo.done())));
    }

    public boolean delete(long id) {
        return store.remove(id) != null;
    }
}
