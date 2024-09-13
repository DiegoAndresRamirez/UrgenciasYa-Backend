package com.urgenciasYa.service.IModel;

import com.urgenciasYa.model.Shift;

public interface IShiftModel {
    Shift createShift(String idNumber, Long hospitalId, Long epsId);
}
