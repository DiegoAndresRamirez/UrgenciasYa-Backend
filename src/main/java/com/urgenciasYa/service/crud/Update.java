package com.urgenciasYa.service.crud;

public interface Update <ID, Entity, I> {
    I update (ID id, Entity entity);
}
