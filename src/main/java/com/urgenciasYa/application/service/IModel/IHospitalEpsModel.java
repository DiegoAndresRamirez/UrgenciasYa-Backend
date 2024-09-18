package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.domain.model.HospitalEps;

import java.util.List;

public interface IHospitalEpsModel{
    HospitalEps create(List<String> epsId, String hospitalId);
}
