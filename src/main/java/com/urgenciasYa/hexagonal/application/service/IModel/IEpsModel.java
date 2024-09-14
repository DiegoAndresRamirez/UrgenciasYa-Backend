package com.urgenciasYa.hexagonal.application.service.IModel;

import com.urgenciasYa.hexagonal.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.hexagonal.domain.model.Eps;
import com.urgenciasYa.hexagonal.application.service.crud.CreateDTO;
import com.urgenciasYa.hexagonal.application.service.crud.Delete;
import com.urgenciasYa.hexagonal.application.service.crud.ReadAll;
import com.urgenciasYa.hexagonal.application.service.crud.Update;

public interface IEpsModel extends ReadAll<Eps>,
        CreateDTO<EpsRequestDTO, Eps>,
        Update<Eps,Integer>,
        Delete<Integer> {


}
