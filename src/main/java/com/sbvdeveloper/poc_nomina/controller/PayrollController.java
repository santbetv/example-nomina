package com.sbvdeveloper.poc_nomina.controller;


import com.sbvdeveloper.poc_nomina.service.ExcelExportRetentionService;
import com.sbvdeveloper.poc_nomina.service.ExcelExportSavingService;
import com.sbvdeveloper.poc_nomina.service.PdfExportSavingService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/exportar")
@AllArgsConstructor
public class PayrollController {

    private ExcelExportRetentionService excelExportRetentionService;
    private ExcelExportSavingService excelExportSavingService;
    private PdfExportSavingService pdfExportSavingService;

    @GetMapping("/xlsx/nomina")
    public void exportRetention(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outputStream = excelExportRetentionService.generarNominaExcel();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=nomina_retencion.xlsx");
        response.getOutputStream().write(outputStream.toByteArray());
        response.getOutputStream().flush();
    }

    @GetMapping("/xlsx/ahorro")
    public void exportSaving(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outputStream = excelExportSavingService.generateSavingExcel();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=nomina_ahorro.xlsx");
        response.getOutputStream().write(outputStream.toByteArray());
        response.getOutputStream().flush();
    }

    @GetMapping("/pdf/{type}")
    public ResponseEntity<byte[]> generateReportRetention(@PathVariable() String type) throws IOException {
        byte[] report = pdfExportSavingService.generateReport(type);
        //byte[] protectedPdf = pdfProtection.protectPdfWithPassword(report, "1234");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "NominaRetención" + ".pdf");
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }
}
