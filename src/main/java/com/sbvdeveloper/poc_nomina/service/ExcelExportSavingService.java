package com.sbvdeveloper.poc_nomina.service;

import com.sbvdeveloper.poc_nomina.dto.ListDetailSavingsDTO;
import com.sbvdeveloper.poc_nomina.dto.ListDetailWithholdingDTO;
import com.sbvdeveloper.poc_nomina.dto.PayrollFileDTO;
import com.sbvdeveloper.poc_nomina.mock.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class ExcelExportSavingService {

    public ByteArrayOutputStream generateSavingExcel() {

        PayrollFileDTO payrollFileDTO = Data.headPayRollSavingXmlx();
        List<ListDetailSavingsDTO> listDetailSavingsDTOS = Data.detailSavingXmlx();

        try {
            InputStream is = ExcelExportSavingService.class.getResourceAsStream("/ahorro_credito_modelo.xlsx");
            if (is != null) {
                Workbook workbook = new XSSFWorkbook(is);
                Sheet sheet = workbook.getSheet("RetencionAhorro");
                if (sheet == null) {
                    sheet = workbook.getSheetAt(0);
                }

                editDataCellAndRow(sheet, 8, 2, payrollFileDTO.getRazonSocial());
                editDataCellAndRow(sheet, 9, 2, payrollFileDTO.getRut());
                editDataCellAndRow(sheet, 10, 2, payrollFileDTO.getDireccion() + " " + payrollFileDTO.getComuna() + " " + payrollFileDTO.getCiudad());
                editDataCellAndRow(sheet, 2, 7, payrollFileDTO.getNumeroBoletin());
                editDataCellAndRow(sheet, 5, 7, payrollFileDTO.getAporte());
                editDataCellAndRow(sheet, 8, 7, payrollFileDTO.getUltimoDiaPago());
                editDataCellAndRow(sheet, 11, 7, payrollFileDTO.getSucursalEmision());


                int headerRowIndex = 16;
                addDetalleRows(sheet, listDetailSavingsDTOS, headerRowIndex);


                int fullPaymentRowIndex = (headerRowIndex + listDetailSavingsDTOS.size() + 2);
                createBorderedRow(sheet, fullPaymentRowIndex, 0, 9, "TIPO AHORRO: A=AHORRO VOLUNTARIO L=LEASING HABITACIONAL");


                int rowIndex = (fullPaymentRowIndex + 2);
                createSingleCellWithBorder(sheet, rowIndex, 7, "TOTAL INFORMADO U.F", styleGrayBoldCenter(sheet));
                createSingleCellWithBorder(sheet, rowIndex + 1, 7, payrollFileDTO.getTotalInformado(), styleCenter(sheet));
                createSingleCellWithBorder(sheet, rowIndex + 2, 7, "TOTAL A PAGAR", styleGrayBoldCenter(sheet));
                createSingleCellWithBorder(sheet, rowIndex + 3, 7, payrollFileDTO.getTotalAPagar(), styleCenter(sheet));

                sheet.setColumnWidth(7, 9000);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);

                // Cerramos el workbook y retornamos
                workbook.close();
                return outputStream;
            } else {
                throw new RuntimeException("Error archivo xlsx vacio");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error en la busqueda del archivo xlsx", ex);
        }
    }


    private void editDataCellAndRow(Sheet sheet, int rowIndex, int cellIndex, String value) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }
        cell.setCellValue(value);
    }

    private void addDetalleRows(Sheet sheet, List<ListDetailSavingsDTO> detalles, int headerRowIndex) {
        // La primera fila de datos será la fila inmediatamente después del encabezado
        int currentRowIndex = headerRowIndex;

        for (ListDetailSavingsDTO detalle : detalles) {
            Row row = sheet.createRow(currentRowIndex++);

            int colIndex = 0;

            Cell cellCorr = row.createCell(colIndex++);
            cellCorr.setCellValue(detalle.getCorr());
            cellCorr.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellRut = row.createCell(colIndex++);
            cellRut.setCellValue(detalle.getNombre());
            cellRut.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellCodigoInterno = row.createCell(colIndex++);
            cellCodigoInterno.setCellValue(detalle.getRutCodigoInterno());
            cellCodigoInterno.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellCuenta = row.createCell(colIndex++);
            cellCuenta.setCellValue(detalle.getCuenta());
            cellCuenta.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellCuota = row.createCell(colIndex++);
            cellCuota.setCellValue(detalle.getCuota());
            cellCuota.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellAporteUf = row.createCell(colIndex++);
            cellAporteUf.setCellValue(detalle.getAporteUf());
            cellAporteUf.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellAporte = row.createCell(colIndex++);
            cellAporte.setCellValue(detalle.getAporte());
            cellAporte.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellTotal = row.createCell(colIndex++);
            cellTotal.setCellValue(detalle.getTotal());
            cellTotal.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellTipoAhorro = row.createCell(colIndex++);
            cellTipoAhorro.setCellValue(detalle.getTipoAhorro());
            cellTipoAhorro.setCellStyle(createCellStyleListDetailText(sheet));

            Cell cellSaldoDeuda = row.createCell(colIndex++);
            cellSaldoDeuda.setCellValue(" ");

        }
    }

    // ------------------------ ESTILOS BÁSICOS -----------------------

    private void createSingleCellWithBorder(Sheet sheet,
                                            int rowIndex,
                                            int colIndex,
                                            String value,
                                            CellStyle style) {

        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }

        cell.setCellValue(value);
        cell.setCellStyle(style);

        CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex);

        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
    }

    private CellStyle styleGrayBoldCenter(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();

        CellStyle styleGrayBoldCenter = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        styleGrayBoldCenter.setFont(boldFont);
        styleGrayBoldCenter.setAlignment(HorizontalAlignment.CENTER);
        styleGrayBoldCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        styleGrayBoldCenter.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleGrayBoldCenter.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        CellStyle styleCenter = workbook.createCellStyle();
        styleCenter.setAlignment(HorizontalAlignment.CENTER);
        styleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

        return styleGrayBoldCenter;
    }

    private CellStyle styleCenter(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();

        CellStyle styleCenter = workbook.createCellStyle();
        styleCenter.setAlignment(HorizontalAlignment.CENTER);
        styleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

        return styleCenter;
    }

    private void createBorderedRow(Sheet sheet, int rowIndex, int firstCol, int lastCol, String value) {
        // 1. Obtener o crear la fila
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        // 2. Fusionar las columnas deseadas en la misma fila
        CellRangeAddress mergedRegion = new CellRangeAddress(rowIndex, rowIndex, firstCol, lastCol);
        sheet.addMergedRegion(mergedRegion);

        // 3. Crear la celda en la primera columna de la fusión
        Cell cell = row.getCell(firstCol);
        if (cell == null) {
            cell = row.createCell(firstCol);
        }
        cell.setCellValue(value);

        // 4. Crear un estilo con bordes
        Workbook workbook = sheet.getWorkbook();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);

        // Bordes en la celda
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        cell.setCellStyle(style);

        // 5. Aplicar bordes a toda la región fusionada con RegionUtil
        RegionUtil.setBorderTop(BorderStyle.THIN, mergedRegion, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, mergedRegion, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, mergedRegion, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, mergedRegion, sheet);
    }

    private CellStyle createCellStyleListDetailText(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        return style;
    }
}
