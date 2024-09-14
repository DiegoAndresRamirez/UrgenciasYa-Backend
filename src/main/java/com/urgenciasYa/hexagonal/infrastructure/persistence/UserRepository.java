package com.urgenciasYa.hexagonal.infrastructure.persistence;

import com.urgenciasYa.hexagonal.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByEmail(String email);
    UserEntity findByDocument(String document);
}
