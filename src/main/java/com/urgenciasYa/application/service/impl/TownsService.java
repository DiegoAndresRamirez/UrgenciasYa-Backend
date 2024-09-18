package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.TownCreateDTO;
import com.urgenciasYa.domain.model.Towns;
import com.urgenciasYa.infrastructure.persistence.TownsRepository;
import com.urgenciasYa.application.service.IModel.ITownsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TownsService implements ITownsModel {

    @Autowired
    TownsRepository townsRepository;

    @Override
    public List<Towns> readALl() {
        return townsRepository.findAll();
    }

    @Override
    public Towns create(TownCreateDTO entity) {
            if(townsRepository.existsByName(entity.getName())){
                throw new IllegalArgumentException("El municipio ya existe en la base de datos");
            }
        Towns town = new Towns();
        town.setName(entity.getName());

        return townsRepository.save(town);
    }

    @Override
    public Towns update(Integer id ,Towns towns) {
        Optional<Towns> optionalTowns = townsRepository.findById(id);
        if(optionalTowns.isPresent()){
            Towns townsdb = optionalTowns.get();
            townsdb.setName(towns.getName());
            return townsRepository.save(townsdb);
        }else {
            throw new IllegalArgumentException("Municipio con el ID "+ id +" No existe");
        }

    }

    @Override
    public void delete(Integer integer) {
        if(townsRepository.existsById(integer)){
          townsRepository.deleteById(integer);
        }else {
            throw new IllegalArgumentException("El municipio con ID " + integer + " No existe");
        }
    }

    @Override
    public Towns getById(Integer id) {
        Optional<Towns> optionalTown = townsRepository.findById(id);
        if (optionalTown.isPresent()) {
            return optionalTown.get();
        } else {
            throw new IllegalArgumentException("Municipio con el ID " + id + " no existe");
        }
    }
}
