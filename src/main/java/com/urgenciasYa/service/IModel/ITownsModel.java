package com.urgenciasYa.service.IModel;

import com.urgenciasYa.dto.request.TownCreateDTO;
import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.service.crud.CreateDTO;
import com.urgenciasYa.service.crud.Delete;
import com.urgenciasYa.service.crud.ReadAll;
import com.urgenciasYa.service.crud.Update;

public interface ITownsModel extends ReadAll<Towns>,
        CreateDTO<TownCreateDTO,Towns>,
        Update<Towns,Integer>,
        Delete<Integer>{
}
