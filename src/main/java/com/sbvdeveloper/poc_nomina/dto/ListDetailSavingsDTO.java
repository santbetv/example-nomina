package com.sbvdeveloper.poc_nomina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListDetailSavingsDTO {
    private String corr;
    private String nombre;
    private String rutCodigoInterno;
    private String cuenta;
    private String cuota;
    private String aporteUf;
    private String aporte;
    private String total;
    private String tipoAhorro;
    private String rebaja;
}
