package com.urgenciasYa.service.Impl;

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
}
