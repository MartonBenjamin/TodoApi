package com.le43er.frameworkProject.model;

import com.le43er.frameworkProject.dao.entity.RoleEntity;
import java.time.LocalDateTime;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import lombok.ToString;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean isActive;
    private LocalDateTime created;
    private Set<Role> roles = new HashSet<>();

    public void addRoles(String roleName) {
        if (this.roles == null || this.roles.isEmpty())
            this.roles = new HashSet<>();
        this.roles.add(new Role(roleName));
    }
}
