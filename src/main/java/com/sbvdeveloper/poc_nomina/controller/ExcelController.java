package com.sbvdeveloper.poc_nomina.controller;


import com.sbvdeveloper.poc_nomina.service.ExcelExportRetentionService;
import com.sbvdeveloper.poc_nomina.service.ExcelExportSavingService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/exportar")
@AllArgsConstructor
public class ExcelController {

    private ExcelExportRetentionService excelExportRetentionService;
    private ExcelExportSavingService excelExportSavingService;

    @GetMapping("/nomina/xlsx")
    public void exportarNomina(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outputStream = excelExportRetentionService.generarNominaExcel();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=nomina_retencion.xlsx");
        response.getOutputStream().write(outputStream.toByteArray());
        response.getOutputStream().flush();
    }

    @GetMapping("/ahorro/xlsx")
    public void exportarAhorro(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outputStream = excelExportSavingService.generateSavingExcel();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=nomina_ahorro.xlsx");
        response.getOutputStream().write(outputStream.toByteArray());
        response.getOutputStream().flush();
    }
}
