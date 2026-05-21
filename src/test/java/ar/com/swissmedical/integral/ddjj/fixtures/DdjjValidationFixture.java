package ar.com.swissmedical.integral.ddjj.fixtures;

import ar.com.swissmedical.integral.ddjj.adapter.mybatis.model.DdjjValidationModel;
import ar.com.swissmedical.integral.ddjj.application.dto.request.DdjjValidationRequestDto;
import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;

public class DdjjValidationFixture {

    public static DdjjValidationRequestDto requestDto(String idCuenta, String idCompania, String idMop) {
        DdjjValidationRequestDto dto = new DdjjValidationRequestDto();
        dto.setIdCuenta(idCuenta);
        dto.setIdCompania(idCompania);
        dto.setIdMop(idMop);
        return dto;
    }

    public static DdjjValidationModel modelResult(String ddjj, String auditoria) {
        DdjjValidationModel model = new DdjjValidationModel();
        model.setDdjj(ddjj);
        model.setAuditoria(auditoria);
        model.setOperador("operador_test");
        model.setInsertado("2024-01-01 00:00:00");
        return model;
    }

    public static DdjjValidationResponseDto responseDto(Boolean requiereDDJJ, Boolean derivaAuditoriaMedica) {
        return new DdjjValidationResponseDto(requiereDDJJ, derivaAuditoriaMedica);
    }

}
