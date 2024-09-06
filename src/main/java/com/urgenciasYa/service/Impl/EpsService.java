package com.urgenciasYa.service.Impl;

import com.urgenciasYa.model.Eps;
import com.urgenciasYa.repository.EpsRepository;
import com.urgenciasYa.service.IModel.IEpsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpsService implements IEpsModel {

    @Autowired
    EpsRepository epsRepository;

    @Override
    public List<Eps> readALl() {
        return epsRepository.findAll();
    }
}
