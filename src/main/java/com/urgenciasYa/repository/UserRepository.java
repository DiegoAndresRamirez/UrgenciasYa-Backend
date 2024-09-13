package com.urgenciasYa.repository;

import com.urgenciasYa.dto.request.UserRegisterDTO;
import com.urgenciasYa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByEmail(String email);
    UserEntity findByDocument(String document);
}
