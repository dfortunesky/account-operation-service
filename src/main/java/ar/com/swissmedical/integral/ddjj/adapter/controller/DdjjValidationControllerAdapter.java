package ar.com.swissmedical.integral.ddjj.adapter.controller;

import ar.com.swissmedical.integral.ddjj.application.dto.request.DdjjValidationRequestDto;
import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;
import ar.com.swissmedical.integral.ddjj.application.port.in.DdjjValidationPortIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/integral/v1/validaciones")
public class DdjjValidationControllerAdapter {

    private final DdjjValidationPortIn ddjjValidationPortIn;

    @GetMapping("/ddjj")
    public ResponseEntity<DdjjValidationResponseDto> validate(
            @RequestParam String idCuenta,
            @RequestParam String idCompania,
            @RequestParam(required = false) String idMop) {

        DdjjValidationRequestDto request = new DdjjValidationRequestDto();
        request.setIdCuenta(idCuenta);
        request.setIdCompania(idCompania);
        request.setIdMop(idMop);

        return ResponseEntity.ok(ddjjValidationPortIn.validate(request));
    }

}
