package com.sbvdeveloper.poc_nomina.service;

import cl.ccla.app.lib_core_pdf_generator.dto.StorageDTO;
import cl.ccla.app.lib_core_pdf_generator.strategy.ReportStrategy;
import com.sbvdeveloper.poc_nomina.config.LoadFilesConfig;
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
public class PdfExportRetentionService {

    private final ReportStrategy reportStrategy;
    private final StorageDTO initStorageBucket;
    private final LoadFilesConfig loadFilesConfig;

    public byte[] generateReport(String pdf) {

        String templateName = this.resolveTemplateName(pdf);
        try {
            var informationDataMapAssigment = createPayrollPdf();

            log.info("Datos aceptados para PDF: " + informationDataMapAssigment);

            return reportStrategy.generateReportPayroll(informationDataMapAssigment, initStorageBucket, "R", templateName.split(", "));

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
            String page1 = "/reporte-jasper/nomina/retencion/nominaRetencion.png";
            String page2 = "/reporte-jasper/nomina/retencion/nominaRetencionHoja2.jpg";
            String page3 = "/reporte-jasper/nomina/retencion/nominaRetencionHoja3.jpg";
            resultMap.put("nominaRetencion", ImageUtils.loadImage(page1));
            resultMap.put("nominaRetencionHoja2", ImageUtils.loadImage(page2));
            resultMap.put("nominaRetencionHoja3", ImageUtils.loadImage(page3));
            setHeadParameters(resultMap);
            getDetailMapNomina(resultMap);
        } catch (IOException e) {
            log.error("Error al procesar la carga de recursos : {0}", e.getMessage());
            throw new RuntimeException("Error al procesar la carga de recursos : ");
        }
        return resultMap;
    }

    public void setHeadParameters(Map<String, Object> parameters) {

        PayrollFileDTO payrollFileDTO = Data.headPayRollRetentionPDF();

        parameters.put("razon_social", payrollFileDTO.getRazonSocial());
        parameters.put("rut", payrollFileDTO.getRut());
        parameters.put("direccion", payrollFileDTO.getDireccion());
        parameters.put("comuna", payrollFileDTO.getComuna());
        parameters.put("periodo", payrollFileDTO.getPeriodo());
        parameters.put("ciudad", payrollFileDTO.getCiudad());
        parameters.put("ultimo_dia_pago", payrollFileDTO.getUltimoDiaPago());
        parameters.put("sucursal_pago", payrollFileDTO.getSucursalPago());
        parameters.put("numero_boletin", payrollFileDTO.getNumeroBoletin());
        parameters.put("total_informado", payrollFileDTO.getTotalInformado());
        parameters.put("total_rebajas", payrollFileDTO.getTotalRebajas());
        parameters.put("total_agregados", payrollFileDTO.getTotalAgregados());
        parameters.put("sub_total", payrollFileDTO.getSubTotal());
        parameters.put("reajustes", payrollFileDTO.getReajustes());
        parameters.put("intereses", payrollFileDTO.getIntereses());
        parameters.put("total", payrollFileDTO.getTotal());
        parameters.put("gtos_cob", payrollFileDTO.getGtosCob());
        parameters.put("efectivo", payrollFileDTO.getEfectivo());
        parameters.put("cheque", payrollFileDTO.getCheque());
        parameters.put("numero", payrollFileDTO.getNumero());
        parameters.put("banco", payrollFileDTO.getBanco());
        parameters.put("sub_total_hoja", payrollFileDTO.getSubTotalHoja());
    }

    public void getDetailMapNomina(Map<String, Object> parameters) {
        parameters.remove("listaDetalle");
        List<ListDetailWithholdingDTO> tableDataList = Data.detailWithHoldingPDF();

        List<Map<String, String>> detailList = tableDataList
                .stream()
                .map(this::mapDetailRetention)
                .toList();

        JRBeanArrayDataSource dataSource = new JRBeanArrayDataSource(detailList.toArray());
        parameters.put("listaDetalle", dataSource);
    }

    private Map<String, String> mapDetailRetention(ListDetailWithholdingDTO listDetailWithholdingDTO) {
        Map<String, String> detailRetention = new HashMap<>();
        detailRetention.put("corr", listDetailWithholdingDTO.getCorr());
        detailRetention.put("rut_beneficiario", listDetailWithholdingDTO.getRutBeneficiario());
        detailRetention.put("nombre_beneficiario", listDetailWithholdingDTO.getNombreBeneficiario());
        detailRetention.put("producto", listDetailWithholdingDTO.getProducto());
        detailRetention.put("codigo", listDetailWithholdingDTO.getCodigo());
        detailRetention.put("cuota", listDetailWithholdingDTO.getCuota());
        detailRetention.put("valor_descontar", listDetailWithholdingDTO.getValorDescontar());
        detailRetention.put("total_descuento_beneficiario", listDetailWithholdingDTO.getTotalDescuentoBeneficiario());
        detailRetention.put("saldo_deuda", listDetailWithholdingDTO.getSaldoDeuda());
        detailRetention.put("abonos_total_deuda", listDetailWithholdingDTO.getAbonosTotalDeuda());
        detailRetention.put("observaciones", listDetailWithholdingDTO.getObservaciones());
        detailRetention.put("linea", listDetailWithholdingDTO.getLinea());
        return detailRetention;
    }

}
