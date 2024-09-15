package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Update;
import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;

public interface IModelEmergencyContact extends Create<EmergencyContactRequestDTO> , Update<EmergencyContactRequestDTO, Long> {
}
