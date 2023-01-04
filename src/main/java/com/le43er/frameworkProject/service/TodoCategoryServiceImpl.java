package com.le43er.frameworkProject.service;

import com.le43er.frameworkProject.dao.TodoCategoryRepository;
import com.le43er.frameworkProject.dao.entity.TodoCategoryEntity;
import com.le43er.frameworkProject.model.TodoCategory;
import com.le43er.frameworkProject.service.Exceptions.TodoCategoryAlreadyExistsException;
import com.le43er.frameworkProject.service.Exceptions.TodoCategoryNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoCategoryServiceImpl implements TodoCategoryService {

  private final TodoCategoryRepository todoCategoryRepository;

  public static TodoCategoryEntity convertTodoCategory2TodoCategoryEntity(
      TodoCategory todoCategory) {
    return TodoCategoryEntity.builder()
        .id(todoCategory.getId())
        .name(todoCategory.getName())
        .description(todoCategory.getDescription())
        .created_at(todoCategory.getCreated_at())
        .build();
  }

  public static TodoCategory convertTodoCategoryEntity2TodoCategory(
      TodoCategoryEntity todoCategoryEntity) {
    return new TodoCategory(
        todoCategoryEntity.getId(),
        todoCategoryEntity.getName(),
        todoCategoryEntity.getDescription(),
        todoCategoryEntity.getCreated_at()
    );
  }

  @Override
  public TodoCategory record(TodoCategory todoCategory) throws TodoCategoryAlreadyExistsException {
    if (todoCategoryRepository.findByName(todoCategory.getName()) != null) {
      throw new TodoCategoryAlreadyExistsException();
    }
    return convertTodoCategoryEntity2TodoCategory(
        todoCategoryRepository.save(convertTodoCategory2TodoCategoryEntity(todoCategory)));
  }

  @Override
  public TodoCategory readByname(String name) throws TodoCategoryNotFoundException {
    TodoCategoryEntity entity = todoCategoryRepository.findByName(name);
    if (entity == null) {
      throw new TodoCategoryNotFoundException(
          String.format("Cannot find todo category with name %s", name));
    }

    return convertTodoCategoryEntity2TodoCategory(
        todoCategoryRepository.findByName(name)
    );
  }

  @Override
  public Collection<TodoCategory> readAll() {
    return todoCategoryRepository.findAll()
        .stream()
        .map(TodoCategoryServiceImpl::convertTodoCategoryEntity2TodoCategory)
        .collect(Collectors.toList());
  }

  @Override
  public TodoCategory modify(TodoCategory TodoCategory) {
    return convertTodoCategoryEntity2TodoCategory(
        todoCategoryRepository.save(convertTodoCategory2TodoCategoryEntity(TodoCategory))
    );
  }

  @Override
  public void delete(String todoCategoryName) throws TodoCategoryNotFoundException {
    TodoCategory todoCategoryToRemove = readByname(todoCategoryName);
    todoCategoryRepository.delete(convertTodoCategory2TodoCategoryEntity(todoCategoryToRemove));
  }
}
