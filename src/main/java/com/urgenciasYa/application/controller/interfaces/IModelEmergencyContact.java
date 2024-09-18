package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.Update;
import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;

public interface IModelEmergencyContact extends Update<EmergencyContactRequestDTO, Long> , Delete<Long> {
}
