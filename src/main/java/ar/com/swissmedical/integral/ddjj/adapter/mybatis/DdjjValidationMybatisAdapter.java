package ar.com.swissmedical.integral.ddjj.adapter.mybatis;

import ar.com.swissmedical.integral.ddjj.adapter.mybatis.mappers.DdjjValidationMapper;
import ar.com.swissmedical.integral.ddjj.adapter.mybatis.model.DdjjValidationModel;
import ar.com.swissmedical.integral.ddjj.application.dto.response.DdjjValidationResponseDto;
import ar.com.swissmedical.integral.ddjj.application.port.out.DdjjValidationRepositoryPortOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DdjjValidationMybatisAdapter implements DdjjValidationRepositoryPortOut {

    private final DdjjValidationMapper mapper;

    @Override
    public DdjjValidationResponseDto getDdjjAuditoria(String idCuenta, String idCompania, String idMop) {
        DdjjValidationModel params = new DdjjValidationModel();
        params.setIdCuenta(idCuenta != null ? Integer.parseInt(idCuenta) : null);
        params.setIdCompania(idCompania != null ? Short.parseShort(idCompania) : null);
        params.setIdMop(idMop);

        DdjjValidationModel result = mapper.getDdjjAuditoria(params);

        return new DdjjValidationResponseDto(
                "S".equals(result.getDdjj()),
                "S".equals(result.getAuditoria())
        );
    }

}
