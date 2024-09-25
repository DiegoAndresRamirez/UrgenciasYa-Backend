package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.application.dto.response.HospitalGetResponseDTO;
import com.urgenciasYa.application.service.crud.*;
import com.urgenciasYa.domain.model.Hospital;

/*
 * Interface for operations related to Hospital management.
 * This interface defines the contract for CRUD operations on Hospital entities.
 */

public interface IHospitalModel extends
        CreateDTO<HospitalCreateResponseDTO, Hospital>, // For creating a Hospital
        Delete<Long>, // For deleting a Hospital by its ID
        ReadById<HospitalGetResponseDTO, Long>, // For retrieving a Hospital by its ID
        ReadAll<HospitalGetResponseDTO> { // For retrieving all Hospitals

    // Updates an existing Hospital entity with new information.
    Hospital update (Long id, HospitalCreateResponseDTO entity);

}
