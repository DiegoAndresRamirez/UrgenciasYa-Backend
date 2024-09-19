package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.application.service.crud.*;
import com.urgenciasYa.domain.model.Eps;

public interface IEpsModel extends ReadAll<Eps>,
        CreateDTO<EpsRequestDTO, Eps>,
        Update<Integer, Eps>,
        Delete<Integer> ,
        ReadById<Eps ,Integer>{


}
