package com.urgenciasYa.service.IModel;

import com.urgenciasYa.model.Shift;

public interface IShiftModel {
    Shift createShift(String document, Long hospitalId, Integer epsId) throws Exception;
}
