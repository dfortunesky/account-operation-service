package ar.com.swissmedical.integral.ddjj.controller;

import ar.com.swissmedical.integral.ddjj.adapter.controller.DdjjValidationControllerAdapter;
import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;
import ar.com.swissmedical.integral.ddjj.application.port.in.DdjjValidationPortIn;
import ar.com.swissmedical.integral.ddjj.fixtures.DdjjValidationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DdjjValidationControllerAdapter.class)
class DdjjValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DdjjValidationPortIn ddjjValidationPortIn;

    @Test
    @DisplayName("GET /ddjj con parámetros válidos → 200 con respuesta correcta")
    void validate_happyPath_returns200() throws Exception {
        when(ddjjValidationPortIn.validate(any()))
                .thenReturn(DdjjValidationFixture.responseDto(true, true));

        mockMvc.perform(get("/integral/v1/validaciones/ddjj")
                        .param("idCuenta", "12345")
                        .param("idCompania", "1")
                        .param("idMop", "0Z0L"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.requiereDDJJ").value(true))
                .andExpect(jsonPath("$.derivaAuditoriaMedica").value(true));
    }

    @Test
    @DisplayName("GET /ddjj sin idMop (opcional) → 200")
    void validate_withoutIdMop_returns200() throws Exception {
        when(ddjjValidationPortIn.validate(any()))
                .thenReturn(DdjjValidationFixture.responseDto(false, false));

        mockMvc.perform(get("/integral/v1/validaciones/ddjj")
                        .param("idCuenta", "12345")
                        .param("idCompania", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.requiereDDJJ").value(false))
                .andExpect(jsonPath("$.derivaAuditoriaMedica").value(false));
    }

    @Test
    @DisplayName("GET /ddjj sin idCompania → 400 con mensaje descriptivo")
    void validate_missingIdCompania_returns400() throws Exception {
        mockMvc.perform(get("/integral/v1/validaciones/ddjj")
                        .param("idCuenta", "12345"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Parámetro requerido ausente: idCompania"));
    }

    @Test
    @DisplayName("GET /ddjj sin idCuenta → 400 con mensaje descriptivo")
    void validate_missingIdCuenta_returns400() throws Exception {
        mockMvc.perform(get("/integral/v1/validaciones/ddjj")
                        .param("idCompania", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Parámetro requerido ausente: idCuenta"));
    }

    @Test
    @DisplayName("GET /ddjj sin ningún parámetro → 400")
    void validate_noParams_returns400() throws Exception {
        mockMvc.perform(get("/integral/v1/validaciones/ddjj"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /ddjj → serialización correcta del DTO de respuesta")
    void validate_responseDto_serializedCorrectly() throws Exception {
        when(ddjjValidationPortIn.validate(any()))
                .thenReturn(new DdjjValidationResponseDto(true, false));

        mockMvc.perform(get("/integral/v1/validaciones/ddjj")
                        .param("idCuenta", "1")
                        .param("idCompania", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requiereDDJJ").value(true))
                .andExpect(jsonPath("$.derivaAuditoriaMedica").value(false));
    }

}
