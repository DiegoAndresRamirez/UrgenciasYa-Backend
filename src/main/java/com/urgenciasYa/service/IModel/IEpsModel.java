package com.urgenciasYa.service.IModel;

import com.urgenciasYa.dto.request.EpsRequestDTO;
import com.urgenciasYa.dto.request.TownCreateDTO;
import com.urgenciasYa.model.Eps;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.service.crud.CreateDTO;
import com.urgenciasYa.service.crud.Delete;
import com.urgenciasYa.service.crud.ReadAll;
import com.urgenciasYa.service.crud.Update;

public interface IEpsModel extends ReadAll<Eps>,
        CreateDTO<EpsRequestDTO, Eps>,
        Update<Eps,Integer>,
        Delete<Integer> {


}
