package ar.com.swissmedical.integral.ddjj.application.usecase;

import ar.com.swissmedical.integral.ddjj.application.dto.request.DdjjValidationRequestDto;
import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;
import ar.com.swissmedical.integral.ddjj.application.port.in.DdjjValidationPortIn;
import ar.com.swissmedical.integral.ddjj.application.port.out.DdjjValidationRepositoryPortOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DdjjValidationUseCase implements DdjjValidationPortIn {

    private final DdjjValidationRepositoryPortOut repositoryPortOut;

    @Override
    public DdjjValidationResponseDto validate(DdjjValidationRequestDto request) {
        return repositoryPortOut.getDdjjAuditoria(
                request.getIdCuenta(),
                request.getIdCompania(),
                request.getIdMop()
        );
    }

}
