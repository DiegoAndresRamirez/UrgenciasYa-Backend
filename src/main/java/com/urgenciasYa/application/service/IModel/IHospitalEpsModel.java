package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.domain.model.HospitalEps;

import java.util.List;

/*
 * Interface for operations related to the association between Hospitals and EPS.
 * This interface defines the contract for creating Hospital-EPS associations.
 */

public interface IHospitalEpsModel{

    //Creates a new association between a list of EPS IDs and a hospital ID.
    HospitalEps create(List<String> epsId, String hospitalId);
}
