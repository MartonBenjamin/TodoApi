package com.le43er.frameworkProject.service;

import com.le43er.frameworkProject.dao.TodoCategoryRepository;
import com.le43er.frameworkProject.dao.TodoRepository;
import com.le43er.frameworkProject.dao.UserRepository;
import com.le43er.frameworkProject.dao.entity.TodoEntity;
import com.le43er.frameworkProject.dao.entity.UserEntity;
import com.le43er.frameworkProject.model.Todo;
import com.le43er.frameworkProject.model.TodoCategory;
import com.le43er.frameworkProject.service.Exceptions.TodoNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  private final TodoCategoryRepository todoCategoryRepositoryRepository;

  private final UserRepository userRepository;
  private final UserService userService;

  private static TodoEntity convertTodo2TodoEntity(
      Todo todo) {
    return TodoEntity.builder()
        .id(todo.getId())
        .name(todo.getName())
        .due_to(todo.getDue_to())
        .created(todo.getCreated())
        .user(UserServiceImpl.convertUser2UserEntity(todo.getUser()))
        .todoCategoryEntity(todo.getTodoCategory() == null ?
            null :
            TodoCategoryServiceImpl.convertTodoCategory2TodoCategoryEntity(todo.getTodoCategory())
        )
        .build();
  }

  private static Todo convertTodoEntity2Todo(TodoEntity todoEntity) {
    return new Todo(
        todoEntity.getId(),
        todoEntity.getName(),
        todoEntity.getDue_to(),
        todoEntity.getCreated(),
        UserServiceImpl.convertUserEntity2User(todoEntity.getUser()),
        todoEntity.getTodoCategoryEntity() == null ? null :
            TodoCategoryServiceImpl.convertTodoCategoryEntity2TodoCategory(
                todoEntity.getTodoCategoryEntity())
    );
  }

  @Override
  public Todo record(Principal principal, Todo todo) {
    todo.setUser(userService.findByUsername(principal.getName()));
    if (todo.getTodoCategory() != null) {
      TodoCategory
          todoCategory = TodoCategoryServiceImpl.convertTodoCategoryEntity2TodoCategory(
          todoCategoryRepositoryRepository.findByName(
              todo.getTodoCategory().getName()));

      if (todoCategory != null) {
        todo.setTodoCategory(todoCategory);
      } else {
        todoCategoryRepositoryRepository.save(
            TodoCategoryServiceImpl.convertTodoCategory2TodoCategoryEntity(todo.getTodoCategory()));
      }
    }
    return convertTodoEntity2Todo(
        todoRepository.save(convertTodo2TodoEntity(todo))
    );
  }

  @Override
  public Todo readByTodo(Todo todo) {
    return convertTodoEntity2Todo(
        todoRepository
            .findTodoEntityByNameAndUser(
                todo.getName()
                , todo.getUser().getId())
    );
  }

  @Override
  public Collection<Todo> readAll() {
    return todoRepository.findAll()
        .stream()
        .map(TodoServiceImpl::convertTodoEntity2Todo)
        .collect(Collectors.toList())
        ;
  }

  @Override
  public Collection<Todo> readByUsername(String username) {
    UserEntity user = userRepository.findByUsername(username);
    return todoRepository.findAllByUserId(user.getId()).stream()
        .map(TodoServiceImpl::convertTodoEntity2Todo).collect(
            Collectors.toList());
  }

  @Override
  public Todo modify(Principal principal, Todo todo) throws TodoNotFoundException {
    Todo todoToModify = readByUsername(principal.getName()).stream()
        .filter(fi -> fi.getId().equals(todo.getId())).collect(Collectors.toList()).get(0);
    if (todoToModify != null) {
      todoToModify.setTodoCategory(todo.getTodoCategory());
      todoToModify.setDue_to(todo.getDue_to());
      todoToModify.setName(todo.getName());
      return convertTodoEntity2Todo(
          todoRepository.save(convertTodo2TodoEntity(todoToModify))
      );
    }
    throw new TodoNotFoundException();
  }

  @Override
  public void delete(Principal principal, Todo todo) throws TodoNotFoundException {
    Todo todoToDelete = readByUsername(principal.getName()).stream()
        .filter(fi -> fi.getName().equals(todo.getName())).collect(Collectors.toList()).get(0);
    if (todoToDelete != null) {
      todoRepository.delete(convertTodo2TodoEntity(todoToDelete));
    } else {
      throw new TodoNotFoundException();
    }
  }
}
