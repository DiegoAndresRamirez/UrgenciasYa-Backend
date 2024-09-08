package com.urgenciasYa.repository;

import com.urgenciasYa.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,String> {
    Optional<RoleEntity> findRoleByCode(String code);
}
