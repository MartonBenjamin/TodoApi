package com.le43er.frameworkProject.service;

import com.le43er.frameworkProject.dao.entity.TodoCategoryEntity;
import com.le43er.frameworkProject.model.TodoCategory;
import com.le43er.frameworkProject.service.Exceptions.TodoCategoryAlreadyExistsException;
import com.le43er.frameworkProject.service.Exceptions.TodoCategoryNotFoundException;

import java.util.Collection;

public interface TodoCategoryService {
    TodoCategory record(TodoCategory todoCategory) throws TodoCategoryAlreadyExistsException;

    TodoCategory readByname(String name) throws TodoCategoryNotFoundException;

    Collection<TodoCategory> readAll();

    TodoCategory modify(TodoCategory todoCategory);

    void delete(String todoCategoryName) throws TodoCategoryNotFoundException;
}
