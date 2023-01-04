package com.le43er.frameworkProject.controller;

import com.le43er.frameworkProject.controller.dto.TodoDto;
import com.le43er.frameworkProject.controller.dto.TodoMapper;
import com.le43er.frameworkProject.service.Exceptions.TodoNotFoundException;
import com.le43er.frameworkProject.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Todos")
@RequestMapping("/todo")
@RestController
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;
  private final TodoMapper todoMapper;

  @ApiOperation("Read By Username")
  @GetMapping(value = "/get")
  public Collection<TodoDto> readTodosByUsername(Principal principal) {
    log.debug("user is getting" + principal.getName());
    return todoService.readByUsername(principal.getName())
        .stream()
        .map(todoMapper::todo2TodoTdo)
        .collect(Collectors.toList());
  }

  @ApiOperation("Add new Todo to user")
  @PostMapping(value = "/add")
  public TodoDto addNewTodo(Principal principal, @RequestBody TodoDto todoDto) {
    log.debug(todoDto.toString());
    return todoMapper
        .todo2TodoTdo(todoService
            .record(principal, todoMapper.todoDto2todo(todoDto))
        );
  }

  @ApiOperation("Modify todo")
  @PutMapping(value = "/modify")
  public TodoDto modifyTodo(Principal principal, @RequestBody TodoDto todoDto)
      throws TodoNotFoundException {
    log.debug(todoMapper.todoDto2todo(todoDto).toString());
    return todoMapper
        .todo2TodoTdo(todoService.modify(principal
            , todoMapper.todoDto2todo(todoDto)));
  }

  @ApiOperation("Delete todo")
  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public String deleteTodo(Principal principal, @RequestBody TodoDto todoDto)
      throws TodoNotFoundException {
    log.debug(todoMapper.todoDto2todo(todoDto).toString());
    todoService.delete(principal, todoMapper.todoDto2todo(todoDto));
    return "ok";

  }
}
