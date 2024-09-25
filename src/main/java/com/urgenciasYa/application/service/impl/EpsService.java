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
    } // Fetching all records from the repository

    @Override
    public Eps create(EpsRequestDTO entity) {
        // Check if an EPS with the same name already exists
        if(epsRepository.existsByName(entity.getName())) throw new IllegalArgumentException("La EPS que intentas crear, ya EXISTE. Rectifica tus datos.");

        Eps eps = new Eps(); // Creating a new Eps object
        eps.setName(entity.getName()); // Setting the name
        return epsRepository.save(eps); // Saving the new EPS to the repository
    }

    @Override
    public void delete(Integer id) {
        // Check if the EPS exists before attempting to delete
        if (!epsRepository.existsById(id)) {
            throw new IllegalArgumentException("La EPS con ID " + id + " no existe.");
        }
        epsRepository.deleteById(id);
    }

    @Override
    public Eps update( Integer id ,Eps eps) {
        Optional<Eps> optionalEps = epsRepository.findById(id); // Fetching the EPS by ID
        if(optionalEps.isPresent()){
            Eps epsdb = optionalEps.get();
            epsdb.setName(eps.getName()); // Update the name
            return epsRepository.save(epsdb); // Save the updated EPS
        }else{
            throw new IllegalArgumentException("La Eps con ID asignado "+ id + "NO existe en nuestra base de datos. Rectifica tus datos");
        }
    }

    @Override
    public Eps getById(Integer id) {
        Optional<Eps> optionalEps = epsRepository.findById(id); // Fetching the EPS by ID
        if (optionalEps.isPresent()) { // Check if it exists
            return optionalEps.get(); // Return the EPS
        } else {
            throw new IllegalArgumentException("EPS con el ID " + id + " no existe"); // Throw an exception if not found
        }
    }
}
