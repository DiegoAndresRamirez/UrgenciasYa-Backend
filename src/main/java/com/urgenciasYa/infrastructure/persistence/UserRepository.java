package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findById(Long id);
    UserEntity findByEmail(String email);
    UserEntity findByDocument(String document);
    UserEntity findByName(String name);
}
