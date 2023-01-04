package com.le43er.frameworkProject.dao;

import com.le43er.frameworkProject.dao.entity.TodoCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoCategoryRepository extends JpaRepository<TodoCategoryEntity, Long> {
    TodoCategoryEntity findByName(String name);
}
