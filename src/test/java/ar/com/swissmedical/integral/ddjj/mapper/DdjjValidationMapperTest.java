package ar.com.swissmedical.integral.ddjj.mapper;

import ar.com.swissmedical.integral.ddjj.adapter.mybatis.DdjjValidationMybatisAdapter;
import ar.com.swissmedical.integral.ddjj.adapter.mybatis.mappers.DdjjValidationMapper;
import ar.com.swissmedical.integral.ddjj.adapter.mybatis.model.DdjjValidationModel;
import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;
import ar.com.swissmedical.integral.ddjj.fixtures.DdjjValidationFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DdjjValidationMapperTest {

    @Mock
    private DdjjValidationMapper mapper;

    @InjectMocks
    private DdjjValidationMybatisAdapter adapter;

    @Test
    @DisplayName("ddjj='S', auditoria='S' → requiereDDJJ=true, derivaAuditoriaMedica=true")
    void getDdjjAuditoria_sybaseS_mapsToTrue() {
        when(mapper.getDdjjAuditoria(any()))
                .thenReturn(DdjjValidationFixture.modelResult("S", "S"));

        DdjjValidationResponseDto result = adapter.getDdjjAuditoria("12345", "1", "0Z0L");

        assertThat(result.getRequiereDDJJ()).isTrue();
        assertThat(result.getDerivaAuditoriaMedica()).isTrue();
    }

    @Test
    @DisplayName("ddjj='N', auditoria='N' → requiereDDJJ=false, derivaAuditoriaMedica=false")
    void getDdjjAuditoria_sybaseN_mapsToFalse() {
        when(mapper.getDdjjAuditoria(any()))
                .thenReturn(DdjjValidationFixture.modelResult("N", "N"));

        DdjjValidationResponseDto result = adapter.getDdjjAuditoria("12345", "1", null);

        assertThat(result.getRequiereDDJJ()).isFalse();
        assertThat(result.getDerivaAuditoriaMedica()).isFalse();
    }

    @Test
    @DisplayName("ddjj='S', auditoria='N' → requiereDDJJ=true, derivaAuditoriaMedica=false")
    void getDdjjAuditoria_ddjjS_auditoriaN() {
        when(mapper.getDdjjAuditoria(any()))
                .thenReturn(DdjjValidationFixture.modelResult("S", "N"));

        DdjjValidationResponseDto result = adapter.getDdjjAuditoria("12345", "1", null);

        assertThat(result.getRequiereDDJJ()).isTrue();
        assertThat(result.getDerivaAuditoriaMedica()).isFalse();
    }

    @Test
    @DisplayName("ddjj='N', auditoria='S' → requiereDDJJ=false, derivaAuditoriaMedica=true")
    void getDdjjAuditoria_ddjjN_auditoriaS() {
        when(mapper.getDdjjAuditoria(any()))
                .thenReturn(DdjjValidationFixture.modelResult("N", "S"));

        DdjjValidationResponseDto result = adapter.getDdjjAuditoria("12345", "1", null);

        assertThat(result.getRequiereDDJJ()).isFalse();
        assertThat(result.getDerivaAuditoriaMedica()).isTrue();
    }

    @Test
    @DisplayName("Conversión correcta: idCuenta → Integer, idCompania → Short")
    void getDdjjAuditoria_typeConversion_parsedCorrectly() {
        when(mapper.getDdjjAuditoria(any()))
                .thenReturn(DdjjValidationFixture.modelResult("N", "N"));

        ArgumentCaptor<DdjjValidationModel> captor = ArgumentCaptor.forClass(DdjjValidationModel.class);
        adapter.getDdjjAuditoria("99999", "5", "ABC");

        verify(mapper).getDdjjAuditoria(captor.capture());
        DdjjValidationModel captured = captor.getValue();

        assertThat(captured.getIdCuenta()).isEqualTo(99999);
        assertThat(captured.getIdCompania()).isEqualTo((short) 5);
        assertThat(captured.getIdMop()).isEqualTo("ABC");
    }

    @Test
    @DisplayName("idCuenta e idCompania null → se pasan como null al mapper sin NPE")
    void getDdjjAuditoria_nullParams_passedAsNull() {
        when(mapper.getDdjjAuditoria(any()))
                .thenReturn(DdjjValidationFixture.modelResult("N", "N"));

        ArgumentCaptor<DdjjValidationModel> captor = ArgumentCaptor.forClass(DdjjValidationModel.class);
        adapter.getDdjjAuditoria(null, null, null);

        verify(mapper).getDdjjAuditoria(captor.capture());
        DdjjValidationModel captured = captor.getValue();

        assertThat(captured.getIdCuenta()).isNull();
        assertThat(captured.getIdCompania()).isNull();
        assertThat(captured.getIdMop()).isNull();
    }

    @Test
    @DisplayName("idMop con espacios (CHAR(6) trimming) → se pasa tal cual al mapper")
    void getDdjjAuditoria_idMopWithPadding_passedAsIs() {
        when(mapper.getDdjjAuditoria(any()))
                .thenReturn(DdjjValidationFixture.modelResult("S", "S"));

        ArgumentCaptor<DdjjValidationModel> captor = ArgumentCaptor.forClass(DdjjValidationModel.class);
        adapter.getDdjjAuditoria("1", "1", "0Z0L  ");

        verify(mapper).getDdjjAuditoria(captor.capture());
        assertThat(captor.getValue().getIdMop()).isEqualTo("0Z0L  ");
    }

    @Test
    @DisplayName("valores null devueltos por Sybase → sin NullPointerException")
    void getDdjjAuditoria_nullResultFromSybase_handledGracefully() {
        DdjjValidationModel emptyResult = new DdjjValidationModel();
        // ddjj y auditoria son null (Sybase devolvió null)
        when(mapper.getDdjjAuditoria(any())).thenReturn(emptyResult);

        DdjjValidationResponseDto result = adapter.getDdjjAuditoria("12345", "1", null);

        // "S".equals(null) → false, no NPE
        assertThat(result.getRequiereDDJJ()).isFalse();
        assertThat(result.getDerivaAuditoriaMedica()).isFalse();
    }

}
