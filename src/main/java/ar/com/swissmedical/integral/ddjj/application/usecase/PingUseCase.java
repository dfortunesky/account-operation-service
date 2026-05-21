package ar.com.swissmedical.integral.ddjj.application.usecase;

import ar.com.swissmedical.integral.ddjj.application.dto.response.PingResponseDto;
import ar.com.swissmedical.integral.ddjj.application.port.in.PingPortIn;
import org.springframework.stereotype.Service;

@Service
public class PingUseCase implements PingPortIn {

    @Override
    public PingResponseDto ping() {
        return new PingResponseDto("ping");
    }

}
