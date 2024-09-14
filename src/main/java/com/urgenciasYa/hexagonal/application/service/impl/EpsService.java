package com.urgenciasYa.hexagonal.application.service.impl;

import com.urgenciasYa.hexagonal.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.model.Eps;
import com.urgenciasYa.repository.EpsRepository;
import com.urgenciasYa.hexagonal.application.service.IModel.IEpsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpsService implements IEpsModel {

    @Autowired
    EpsRepository epsRepository;

    @Override
    public List<Eps> readALl() {
        return epsRepository.findAll();
    }

    @Override
    public Eps create(EpsRequestDTO entity) {
        if(epsRepository.existsByName(entity.getName())) throw new IllegalArgumentException("La EPS que intentas crear, ya EXISTE. Rectifica tus datos.");

        Eps eps = new Eps();
        eps.setName(entity.getName());
        return epsRepository.save(eps);
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Eps update(Eps eps, Integer id) {
        Optional<Eps> optionalEps = epsRepository.findById(id);
        if(optionalEps.isPresent()){
            Eps epsdb = optionalEps.get();
            epsdb.setName(eps.getName());
            return epsRepository.save(epsdb);
        }else{
            throw new IllegalArgumentException("La Eps con ID asignado "+ id + "NO existe en nuestra base de datos. Rectifica tus datos");
        }
    }
}
