package com.urgenciasYa.service.Impl;

import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.repository.TownsRepository;
import com.urgenciasYa.service.IModel.ITownsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownsService implements ITownsModel {

    @Autowired
    TownsRepository townsRepository;

    @Override
    public List<Towns> readALl() {
        return townsRepository.findAll();
    }

//    @Override
//    public Towns create(Towns towns) {
//        if(townsRepository.existByName(towns.getName()))
//            throw new RuntimeException("El municipio ya existe en la base de datos");
//        Towns town = new Towns();
//        town.setName(towns.getName());
//
//        return townsRepository.save(town);
//    }

    @Override
    public Towns create(TownsDTO entity) {
        if(townsRepository.existByName(entity.getName()))
            throw new RuntimeException("El municipio ya existe en la base de datos");
        Towns town = new Towns();
        town.setName(entity.getName());

        return townsRepository.save(town);
    }
}
