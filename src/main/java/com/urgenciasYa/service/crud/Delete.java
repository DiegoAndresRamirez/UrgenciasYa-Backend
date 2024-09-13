package com.urgenciasYa.service.crud;

public interface Delete <Entity,ID>{
    void delete(Entity entity , ID id);
}
