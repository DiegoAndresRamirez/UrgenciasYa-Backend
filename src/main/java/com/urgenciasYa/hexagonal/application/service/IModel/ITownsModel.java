package com.urgenciasYa.hexagonal.application.service.IModel;

import com.urgenciasYa.dto.request.TownCreateDTO;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.hexagonal.application.service.crud.CreateDTO;
import com.urgenciasYa.hexagonal.application.service.crud.Delete;
import com.urgenciasYa.hexagonal.application.service.crud.ReadAll;
import com.urgenciasYa.hexagonal.application.service.crud.Update;

public interface ITownsModel extends ReadAll<Towns>,
        CreateDTO<TownCreateDTO,Towns>,
        Update<Towns,Integer>,
        Delete<Integer>{
}
