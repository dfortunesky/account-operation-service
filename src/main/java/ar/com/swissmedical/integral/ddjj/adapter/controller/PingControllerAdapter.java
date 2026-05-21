package ar.com.swissmedical.integral.ddjj.adapter.controller;

import ar.com.swissmedical.integral.ddjj.application.dto.response.PingResponseDto;
import ar.com.swissmedical.integral.ddjj.application.port.in.PingPortIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PingControllerAdapter {

    private final PingPortIn pingPortIn;

    @GetMapping("/ping")
    public ResponseEntity<PingResponseDto> ping() {
        return ResponseEntity.ok(pingPortIn.ping());
    }

}
