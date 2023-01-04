package com.le43er.frameworkProject.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
    private Long id;
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
