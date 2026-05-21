package ar.com.swissmedical.integral.ddjj.application.port.out;

import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;

public interface DdjjValidationRepositoryPortOut {

    DdjjValidationResponseDto getDdjjAuditoria(String idCuenta, String idCompania, String idMop);

}
