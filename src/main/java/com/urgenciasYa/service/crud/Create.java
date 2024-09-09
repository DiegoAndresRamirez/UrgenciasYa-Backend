package com.urgenciasYa.service.crud;

import com.urgenciasYa.dto.request.UserRegisterDTO;

public interface Create <Entity> {
    public Entity create(UserRegisterDTO userRegisterDTO);
}
