package ar.com.swissmedical.integral.ddjj.adapter.controller;

import ar.com.swissmedical.integral.ddjj.application.port.in.PingPortIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PingControllerAdapter {

    private final PingPortIn pingPortIn;

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return pingPortIn.ping();
    }

}
