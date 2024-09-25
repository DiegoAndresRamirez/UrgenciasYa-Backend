package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.domain.model.Shift;

/*
 * Interface for operations related to Shift management.
 * This interface defines the contract for creating Shift entities.
 */

public interface IShiftModel {

    //Creates a new Shift for a given user document in a specified hospital and EPS.
    Shift createShift(String document, Long hospitalId, Integer epsId) throws Exception;
}
