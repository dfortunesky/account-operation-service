package ar.com.swissmedical.integral.ddjj.service;

import ar.com.swissmedical.integral.ddjj.application.dto.request.DdjjValidationRequestDto;
import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;
import ar.com.swissmedical.integral.ddjj.application.port.out.DdjjValidationRepositoryPortOut;
import ar.com.swissmedical.integral.ddjj.application.usecase.DdjjValidationUseCase;
import ar.com.swissmedical.integral.ddjj.fixtures.DdjjValidationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DdjjValidationServiceTest {

    @Mock
    private DdjjValidationRepositoryPortOut repositoryPortOut;

    @InjectMocks
    private DdjjValidationUseCase useCase;

    @Test
    @DisplayName("ddjj=S y auditoria=S → requiereDDJJ=true, derivaAuditoriaMedica=true")
    void validate_ddjjS_auditoriaS_returnsBothTrue() {
        DdjjValidationRequestDto request = DdjjValidationFixture.requestDto("12345", "1", "0Z0L");
        when(repositoryPortOut.getDdjjAuditoria("12345", "1", "0Z0L"))
                .thenReturn(DdjjValidationFixture.responseDto(true, true));

        DdjjValidationResponseDto result = useCase.validate(request);

        assertThat(result.getRequiereDDJJ()).isTrue();
        assertThat(result.getDerivaAuditoriaMedica()).isTrue();
    }

    @Test
    @DisplayName("ddjj=N y auditoria=N → requiereDDJJ=false, derivaAuditoriaMedica=false")
    void validate_ddjjN_auditoriaН_returnsBothFalse() {
        DdjjValidationRequestDto request = DdjjValidationFixture.requestDto("12345", "1", null);
        when(repositoryPortOut.getDdjjAuditoria("12345", "1", null))
                .thenReturn(DdjjValidationFixture.responseDto(false, false));

        DdjjValidationResponseDto result = useCase.validate(request);

        assertThat(result.getRequiereDDJJ()).isFalse();
        assertThat(result.getDerivaAuditoriaMedica()).isFalse();
    }

    @Test
    @DisplayName("ddjj=S y auditoria=N → requiereDDJJ=true, derivaAuditoriaMedica=false")
    void validate_ddjjS_auditoriaН_returnsMixed() {
        DdjjValidationRequestDto request = DdjjValidationFixture.requestDto("12345", "1", null);
        when(repositoryPortOut.getDdjjAuditoria("12345", "1", null))
                .thenReturn(DdjjValidationFixture.responseDto(true, false));

        DdjjValidationResponseDto result = useCase.validate(request);

        assertThat(result.getRequiereDDJJ()).isTrue();
        assertThat(result.getDerivaAuditoriaMedica()).isFalse();
    }

    @Test
    @DisplayName("ddjj=N y auditoria=S → requiereDDJJ=false, derivaAuditoriaMedica=true")
    void validate_ddjjN_auditoriaS_returnsMixed() {
        DdjjValidationRequestDto request = DdjjValidationFixture.requestDto("12345", "1", null);
        when(repositoryPortOut.getDdjjAuditoria("12345", "1", null))
                .thenReturn(DdjjValidationFixture.responseDto(false, true));

        DdjjValidationResponseDto result = useCase.validate(request);

        assertThat(result.getRequiereDDJJ()).isFalse();
        assertThat(result.getDerivaAuditoriaMedica()).isTrue();
    }

    @Test
    @DisplayName("idMop null → se delega al repositorio con idMop null")
    void validate_withNullIdMop_delegatesCorrectly() {
        DdjjValidationRequestDto request = DdjjValidationFixture.requestDto("12345", "1", null);
        when(repositoryPortOut.getDdjjAuditoria("12345", "1", null))
                .thenReturn(DdjjValidationFixture.responseDto(true, false));

        DdjjValidationResponseDto result = useCase.validate(request);

        assertThat(result).isNotNull();
        assertThat(result.getRequiereDDJJ()).isTrue();
    }

    @Test
    @DisplayName("modo operación inexistente (idMop desconocido) → respuesta sin error")
    void validate_unknownIdMop_returnsResponse() {
        DdjjValidationRequestDto request = DdjjValidationFixture.requestDto("12345", "1", "XXXXX");
        when(repositoryPortOut.getDdjjAuditoria("12345", "1", "XXXXX"))
                .thenReturn(DdjjValidationFixture.responseDto(false, false));

        DdjjValidationResponseDto result = useCase.validate(request);

        assertThat(result).isNotNull();
    }

}
