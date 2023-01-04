package com.le43er.frameworkProject.service;

import com.le43er.frameworkProject.dao.entity.TodoEntity;
import com.le43er.frameworkProject.model.Todo;
import com.le43er.frameworkProject.service.Exceptions.TodoCategoryNotFoundException;
import com.le43er.frameworkProject.service.Exceptions.TodoNotFoundException;

import java.security.Principal;
import java.util.Collection;

public interface TodoService {
    Todo record(Principal principal, Todo todo);
    Todo readByTodo(Todo todo);
    Collection<Todo> readAll();
    Collection<Todo> readByUsername(String username);
    Todo modify(Principal principal, Todo todo) throws TodoNotFoundException;
    void delete(Principal principal, Todo todo) throws TodoNotFoundException;
}
