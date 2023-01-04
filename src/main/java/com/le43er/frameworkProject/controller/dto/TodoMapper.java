package com.le43er.frameworkProject.controller.dto;

import com.le43er.frameworkProject.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TodoCategoryMapper.class})
public interface TodoMapper {
    TodoDto todo2TodoTdo(Todo todo);

    Todo todoDto2todo(TodoDto todoDto);
}
