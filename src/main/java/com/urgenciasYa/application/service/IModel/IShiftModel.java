package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.domain.model.Shift;

public interface IShiftModel {
    Shift createShift(String document, Long hospitalId, Integer epsId) throws Exception;
}
