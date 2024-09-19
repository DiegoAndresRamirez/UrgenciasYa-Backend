package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.application.service.IModel.IEpsModel;
import com.urgenciasYa.domain.model.Eps;
import com.urgenciasYa.infrastructure.persistence.EpsRepository;
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
    public void delete(Integer id) {
        if (!epsRepository.existsById(id)) {
            throw new IllegalArgumentException("La EPS con ID " + id + " no existe.");
        }
        epsRepository.deleteById(id);
    }

    @Override
    public Eps update( Integer id ,Eps eps) {
        Optional<Eps> optionalEps = epsRepository.findById(id);
        if(optionalEps.isPresent()){
            Eps epsdb = optionalEps.get();
            epsdb.setName(eps.getName());
            return epsRepository.save(epsdb);
        }else{
            throw new IllegalArgumentException("La Eps con ID asignado "+ id + "NO existe en nuestra base de datos. Rectifica tus datos");
        }
    }

    @Override
    public Eps getById(Integer id) {
        Optional<Eps> optionalEps = epsRepository.findById(id);
        if (optionalEps.isPresent()) {
            return optionalEps.get();
        } else {
            throw new IllegalArgumentException("EPS con el ID " + id + " no existe");
        }
    }
}
