package com.sbvdeveloper.poc_nomina.service;

import cl.ccla.app.lib_core_pdf_generator.dto.StorageDTO;
import cl.ccla.app.lib_core_pdf_generator.strategy.ReportStrategy;
import com.sbvdeveloper.poc_nomina.config.LoadFilesConfig;
import com.sbvdeveloper.poc_nomina.dto.ListDetailSavingsDTO;
import com.sbvdeveloper.poc_nomina.dto.ListDetailWithholdingDTO;
import com.sbvdeveloper.poc_nomina.dto.PayrollFileDTO;
import com.sbvdeveloper.poc_nomina.mock.Data;
import com.sbvdeveloper.poc_nomina.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfExportSavingService {

    private final ReportStrategy reportStrategy;
    private final StorageDTO initStorageBucket;
    private final LoadFilesConfig loadFilesConfig;

    public byte[] generateReport(String pdf) {

        String templateName = this.resolveTemplateName(pdf);
        try {
            var informationDataMapAssigment = createPayrollPdf();

            log.info("Datos aceptados para PDF: " + informationDataMapAssigment);

            return reportStrategy.generateReportPayroll(informationDataMapAssigment, initStorageBucket, "A", templateName.split(", "));

        } catch (JRException | IOException e) {
            log.error("No se encontro el documento : {0}", e.getMessage());
            throw new RuntimeException("No se encontro el documento : " + pdf);
        }
    }

    private String resolveTemplateName(String pdf) {
        String templateName = loadFilesConfig.load().get(pdf);
        if (templateName == null || templateName.isEmpty()) {
            log.error("No se encontró el documento");
            throw new RuntimeException("No se encontró el documento: " + pdf);
        }
        return templateName;
    }

    public Map<String, Object> createPayrollPdf() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String page1 = "/reporte-jasper/nomina/ahorro/nominaAhorro.png";
            resultMap.put("nominaAhorro", ImageUtils.loadImage(page1));
            setHeadParameters(resultMap);
            getDetailMapSaving(resultMap);
        } catch (IOException e) {
            log.error("Error al procesar la carga de recursos : {0}", e.getMessage());
            throw new RuntimeException("Error al procesar la carga de recursos : ");
        }
        return resultMap;
    }

    public void setHeadParameters(Map<String, Object> parameters) {

        PayrollFileDTO payrollFileDTO = Data.headPayRollSavingPDF();

        parameters.put("razon_social", payrollFileDTO.getRazonSocial());
        parameters.put("rut", payrollFileDTO.getRut());
        parameters.put("direccion", payrollFileDTO.getDireccion());
        parameters.put("comuna", payrollFileDTO.getComuna());
        parameters.put("ciudad", payrollFileDTO.getCiudad());
        parameters.put("telefono", payrollFileDTO.getTelefono());
        parameters.put("aporte", payrollFileDTO.getAporte());
        parameters.put("sucursal_emision", payrollFileDTO.getSucursalEmision());
        parameters.put("ultimo_dia_pago", payrollFileDTO.getUltimoDiaPago());
        parameters.put("numero_boletin", payrollFileDTO.getNumeroBoletin());
        parameters.put("sub_total_uf", payrollFileDTO.getSubTotalUf());
        parameters.put("sub_total", payrollFileDTO.getSubTotal());
        parameters.put("total_a_pagar", payrollFileDTO.getTotalAPagar());
    }

    public void getDetailMapSaving(Map<String, Object> parameters) {
        parameters.remove("listaDetalle");
        List<ListDetailSavingsDTO> tableDataList = Data.detailSavingPDF();

        List<Map<String, String>> detailList = tableDataList
                .stream()
                .map(this::mapDetailRetention)
                .toList();

        JRBeanArrayDataSource dataSource = new JRBeanArrayDataSource(detailList.toArray());
        parameters.put("listaDetalle", dataSource);
    }

    private Map<String, String> mapDetailRetention(ListDetailSavingsDTO listDetailSavingsDTO) {
        Map<String, String> detailRetention = new HashMap<>();
        detailRetention.put("corr", listDetailSavingsDTO.getCorr());
        detailRetention.put("nombre", listDetailSavingsDTO.getNombre());
        detailRetention.put("rut_codigo_interno", listDetailSavingsDTO.getRutCodigoInterno());
        detailRetention.put("cuenta", listDetailSavingsDTO.getCuenta());
        detailRetention.put("cuota", listDetailSavingsDTO.getCuota());
        detailRetention.put("aporte_uf", listDetailSavingsDTO.getAporteUf());
        detailRetention.put("aporte", listDetailSavingsDTO.getAporte());
        detailRetention.put("total", listDetailSavingsDTO.getTotal());
        detailRetention.put("tipo_ahorro", listDetailSavingsDTO.getTipoAhorro());
        detailRetention.put("rebaja", listDetailSavingsDTO.getRebaja());
        return detailRetention;
    }

}
