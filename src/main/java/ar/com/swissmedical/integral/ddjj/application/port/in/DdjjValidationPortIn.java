package ar.com.swissmedical.integral.ddjj.application.port.in;

import ar.com.swissmedical.integral.ddjj.application.dto.request.DdjjValidationRequestDto;
import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;

public interface DdjjValidationPortIn {

    DdjjValidationResponseDto validate(DdjjValidationRequestDto request);

}
