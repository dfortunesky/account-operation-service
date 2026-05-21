package ar.com.swissmedical.integral.ddjj.application.usecase;

import ar.com.swissmedical.integral.ddjj.application.port.in.PingPortIn;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PingUseCase implements PingPortIn {

    @Override
    public Map<String, String> ping() {
        return Map.of("message", "ping");
    }

}
