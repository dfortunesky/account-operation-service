package ar.com.swissmedical.integral.ddjj.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DdjjValidationResponseDto {

    private Boolean requiereDDJJ;
    private Boolean derivaAuditoriaMedica;

}
