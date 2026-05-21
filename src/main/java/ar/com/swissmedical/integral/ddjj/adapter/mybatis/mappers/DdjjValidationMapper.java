package ar.com.swissmedical.integral.ddjj.adapter.mybatis.mappers;

import ar.com.swissmedical.integral.ddjj.adapter.mybatis.model.DdjjValidationModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DdjjValidationMapper {

    DdjjValidationModel getDdjjAuditoria(DdjjValidationModel params);

}
