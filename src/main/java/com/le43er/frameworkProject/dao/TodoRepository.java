package com.le43er.frameworkProject.dao;

import com.le43er.frameworkProject.dao.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Collection<TodoEntity> findAllByUserId(Long userId);
    TodoEntity findTodoEntityByNameAndUser(String name, Long userId);
}
