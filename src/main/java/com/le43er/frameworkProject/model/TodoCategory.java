package com.le43er.frameworkProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import lombok.ToString;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoCategory {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime created_at;
}
