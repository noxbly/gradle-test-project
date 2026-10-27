package com.example.demo.todo;

/** 할 일 한 건. 샘플이라 DB 없이 메모리에만 보관한다. */
public record Todo(long id, String title, boolean done) {

    public Todo withDone(boolean done) {
        return new Todo(id, title, done);
    }
}
