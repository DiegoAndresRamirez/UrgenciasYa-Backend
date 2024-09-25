package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.Update;
import com.urgenciasYa.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.application.dto.response.EpsResponseDTO;
import com.urgenciasYa.domain.model.Eps;
import org.springframework.http.ResponseEntity;
import java.util.List;

/*
 * Interface representing the model for EPS (Entidad Promotora de Salud).
 * This interface extends generic interfaces for creating, updating, and deleting EPS entities.
 */

public interface IModelEps extends Create<EpsRequestDTO>, Update<Eps,Integer>, Delete<Integer> {

    //Retrieves a list of all EPS entities in the system.
    public ResponseEntity<List<EpsResponseDTO>> getAllEps();
}
