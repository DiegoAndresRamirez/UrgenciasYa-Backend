package com.urgenciasYa.hexagonal.application.service.crud;

public interface Update <ID, Entity, I> {
    I update (ID id, Entity entity);
}
