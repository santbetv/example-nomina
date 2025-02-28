package com.sbvdeveloper.poc_nomina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListDetailWithholdingDTO {

    private String corr;
    private String rutBeneficiario;
    private String nombreBeneficiario;
    private String producto;
    private String codigo;
    private String cuota;
    private String valorDescontar;
    private String totalDescuentoBeneficiario;
    private String saldoDeuda;
    private String abonosTotalDeuda;
    private String observaciones;
    private String linea;
}
