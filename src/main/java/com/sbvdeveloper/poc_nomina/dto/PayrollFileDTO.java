package com.sbvdeveloper.poc_nomina.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollFileDTO {
    private String razonSocial;
    private String rut;
    private String direccion;
    private String comuna;
    private String periodo;
    private String ciudad;
    private String telefono;
    private String aporte;
    private String sucursalPago;
    private String sucursalEmision;
    private String ultimoDiaPago;
    private String numeroBoletin;
    private String inicioPagina;
    private String subTotal;
    private String finalPagina;
    private String subTotalUf;
    private String totalAPagar;
    private String subTotalHoja;
    private String totalInformado;
    private String totalRebajas;
    private String totalAgregados;
    private String reajustes;
    private String intereses;
    private String total;
    private String gtosCob;
    private String efectivo;
    private String cheque;
    private String numero;
    private String banco;
}
