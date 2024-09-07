package com.urgenciasYa.controller.generic;

import com.urgenciasYa.dto.request.UserRegisterDTO;
import org.springframework.http.ResponseEntity;

public interface Create <String> {
    public ResponseEntity<String> create(UserRegisterDTO userRegisterDTO);
}
