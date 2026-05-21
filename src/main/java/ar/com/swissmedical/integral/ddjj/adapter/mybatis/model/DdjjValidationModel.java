package ar.com.swissmedical.integral.ddjj.adapter.mybatis.model;

import lombok.Data;

@Data
public class DdjjValidationModel {

    // Parámetros de entrada al SP
    private Integer idCuenta;
    private Short idCompania;
    private String idMop;

    // Columnas devueltas por el SP
    private String ddjj;       // 'S' o 'N'
    private String auditoria;  // 'S' o 'N'
    private String operador;
    private String insertado;

}
