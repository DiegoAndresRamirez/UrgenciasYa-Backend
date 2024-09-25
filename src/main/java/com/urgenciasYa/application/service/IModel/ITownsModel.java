package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.service.crud.ReadById;
import com.urgenciasYa.application.dto.request.TownCreateDTO;
import com.urgenciasYa.domain.model.Towns;
import com.urgenciasYa.application.service.crud.CreateDTO;
import com.urgenciasYa.application.service.crud.Delete;
import com.urgenciasYa.application.service.crud.ReadAll;
import com.urgenciasYa.application.service.crud.Update;

/*
 * Interface for operations related to Town management.
 * This interface extends the CRUD operations for the Towns model.
 */

public interface ITownsModel extends ReadAll<Towns>,
        CreateDTO<TownCreateDTO,Towns>,
        Update<Integer, Towns>,
        Delete<Integer>,
        ReadById<Towns, Integer>{ // Interface for reading a town by its ID
}
