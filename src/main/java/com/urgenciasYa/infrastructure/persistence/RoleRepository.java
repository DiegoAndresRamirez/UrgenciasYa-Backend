package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,String> {
    Optional<RoleEntity> findRoleByCode(String code);
}
