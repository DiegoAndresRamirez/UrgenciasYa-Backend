package com.urgenciasYa.service.Impl;

import com.urgenciasYa.dto.request.TownCreateDTO;
import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.repository.TownsRepository;
import com.urgenciasYa.service.IModel.ITownsModel;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Towns create(TownCreateDTO entity) {
        if(townsRepository.existsByName(entity.getName()))
            throw new IllegalArgumentException("El municipio ya existe en la base de datos");
        Towns town = new Towns();
        town.setName(entity.getName());

        return townsRepository.save(town);
    }

    @Override
    public Towns update (Towns towns,Integer id) {
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

}
