package com.urgenciasYa.hexagonal.application.service.IModel;

import com.urgenciasYa.hexagonal.domain.model.Shift;

public interface IShiftModel {
    Shift createShift(String document, Long hospitalId, Integer epsId) throws Exception;
}
