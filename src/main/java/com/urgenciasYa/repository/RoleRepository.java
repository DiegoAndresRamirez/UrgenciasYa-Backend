package com.urgenciasYa.repository;

import com.urgenciasYa.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,String> {
    Boolean findRoleByCode(String code);
}
