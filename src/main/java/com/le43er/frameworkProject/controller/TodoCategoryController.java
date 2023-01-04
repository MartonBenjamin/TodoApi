package com.le43er.frameworkProject.controller;

import com.le43er.frameworkProject.controller.dto.TodoCategoryDto;
import com.le43er.frameworkProject.controller.dto.TodoCategoryMapper;
import com.le43er.frameworkProject.model.TodoCategory;
import com.le43er.frameworkProject.service.Exceptions.TodoCategoryAlreadyExistsException;
import com.le43er.frameworkProject.service.Exceptions.TodoCategoryNotFoundException;
import com.le43er.frameworkProject.service.TodoCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "TodoCategories")
@RequestMapping("/todo/category")
@RestController
@RequiredArgsConstructor
public class TodoCategoryController {

  private final TodoCategoryMapper todoCategoryMapper;
  private final TodoCategoryService todoCategoryService;

  @ApiOperation("Get Todo Categories")
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Collection<TodoCategoryDto> getCategories() {
    return todoCategoryService.readAll()
        .stream()
        .map(todoCategoryMapper::todoCategory2TodoCategoryDto)
        .collect(Collectors.toList())
        ;
  }

  @ApiOperation("Add new Todo Category")
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public TodoCategoryDto addCategory(@RequestBody TodoCategoryDto todoCategoryDto)
      throws TodoCategoryAlreadyExistsException {
    TodoCategory todoCategory = todoCategoryMapper.todoCategoryDto2TodoCategory(todoCategoryDto);
    TodoCategory insertedCategory = todoCategoryService.record(todoCategory);

    log.debug(todoCategory.toString());
    return todoCategoryMapper.todoCategory2TodoCategoryDto(insertedCategory);
  }

  @ApiOperation("Modify Todo Category")
  @RequestMapping(value = "/modify", method = RequestMethod.PUT)
  public TodoCategoryDto modifyCategory(@RequestBody TodoCategoryDto todoCategoryDto) {
    TodoCategory todoCategory = todoCategoryMapper.todoCategoryDto2TodoCategory(todoCategoryDto);
    TodoCategory updatedTodoCategory = todoCategoryService.modify(todoCategory);
    return todoCategoryMapper.todoCategory2TodoCategoryDto(updatedTodoCategory);
  }

  @ApiOperation("Delete Todo Category")
  @DeleteMapping(value = "/delete/{name}")
  public String deleteCategory(@PathVariable String name)
      throws TodoCategoryNotFoundException {
    todoCategoryService.delete(name);
    log.debug("removed category:" + name);
    return "ok";
  }
}
