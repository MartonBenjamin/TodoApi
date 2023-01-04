package com.le43er.frameworkProject.dao;

import com.le43er.frameworkProject.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);
}
