package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.domain.model.Eps;
import com.urgenciasYa.application.service.crud.CreateDTO;
import com.urgenciasYa.application.service.crud.Delete;
import com.urgenciasYa.application.service.crud.ReadAll;
import com.urgenciasYa.application.service.crud.Update;

public interface IEpsModel extends ReadAll<Eps>,
        CreateDTO<EpsRequestDTO, Eps>,
        Update<Eps,Integer>,
        Delete<Integer> {


}
