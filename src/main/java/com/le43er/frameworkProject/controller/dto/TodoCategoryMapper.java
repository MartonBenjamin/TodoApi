package com.le43er.frameworkProject.controller.dto;

import com.le43er.frameworkProject.model.TodoCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoCategoryMapper {
    TodoCategoryDto todoCategory2TodoCategoryDto(TodoCategory todoCategory);

    TodoCategory todoCategoryDto2TodoCategory(TodoCategoryDto todoCategoryDto);
}
