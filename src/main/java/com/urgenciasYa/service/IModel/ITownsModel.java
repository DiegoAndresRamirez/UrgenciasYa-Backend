package com.urgenciasYa.service.IModel;

import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.service.crud.CreateDTO;
import com.urgenciasYa.service.crud.ReadAll;

public interface ITownsModel extends ReadAll<Towns>, CreateDTO<TownsDTO,Towns> {
}
