package com.sbvdeveloper.poc_nomina.service;

import com.sbvdeveloper.poc_nomina.dto.LineDataDTO;
import com.sbvdeveloper.poc_nomina.dto.ListDetailWithholdingDTO;
import com.sbvdeveloper.poc_nomina.dto.PayrollFileDTO;
import com.sbvdeveloper.poc_nomina.mock.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelExportRetentionService {

   /* public ByteArrayOutputStream generarNominaExcel() throws IOException {

        // 1. Crear libro y hoja
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Retenciones");

        // 2. Crear estilos básicos
        CellStyle boldStyle   = createBoldStyle(workbook);
        CellStyle rightStyle  = createRightStyle(workbook);
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle   = createDataStyle(workbook);
        CellStyle obsStyle    = createObsStyle(workbook);
        CellStyle cellStyle   = createTableCellStyle(workbook);

        // ----------------------------------------------------------------
        // 3. Encabezado Izquierdo (Filas 0..3, Columna 0)
        // ----------------------------------------------------------------
        sheet.createRow(7).createCell(0).setCellValue("C.C.A.F. DE LOS ANDES");
        sheet.createRow(8).createCell(0).setCellValue("SANCADO");
        sheet.createRow(9).createCell(0).setCellValue("SANTA MARIA 125");
        sheet.createRow(10).createCell(0).setCellValue("Arica Arica");
        sheet.createRow(11).createCell(0).setCellValue("NOTA");
        sheet.createRow(12).createCell(0).setCellValue("no existe...");

        // ----------------------------------------------------------------
        // 4. Información Derecha (Filas 0..5, Columnas 5 y 6)
        // ----------------------------------------------------------------

        // 3) Crea la pequeña tabla en columnas E-F (índices 4 y 5) a partir de la fila 0
        int startRow = 6;
        int col1 = 8; // Columna E
        int col2 = 9; // Columna F

        // Fila 0 (Encabezados de la tabla)
        Row row0 = sheet.createRow(startRow++);
        Cell c0_1 = row0.createCell(col1);
        Cell c0_2 = row0.createCell(col2);
        c0_1.setCellValue("Período");
        c0_1.setCellStyle(headerStyle);
        c0_2.setCellValue("Último día de Pago");
        c0_2.setCellStyle(headerStyle);

        // Fila 1 (Valores del Período y Último día)
        Row row1 = sheet.createRow(startRow++);
        Cell c1_1 = row1.createCell(col1);
        Cell c1_2 = row1.createCell(col2);
        c1_1.setCellValue("Noviembre De 2024");
        c1_1.setCellStyle(cellStyle);
        c1_2.setCellValue("10-Diciembre-2024");
        c1_2.setCellStyle(cellStyle);

        // Fila 2 (Encabezado: Sucursal de Pago)
        Row row2 = sheet.createRow(startRow++);
        Cell c2_1 = row2.createCell(col1);
        Cell c2_2 = row2.createCell(col2);
        c2_1.setCellValue("Sucursal de Pago");
        c2_1.setCellStyle(headerStyle);
        c2_2.setCellValue("");
        c2_2.setCellStyle(headerStyle);

        // Fila 3 (Valor de la Sucursal)
        Row row3 = sheet.createRow(startRow++);
        Cell c3_1 = row3.createCell(col1);
        Cell c3_2 = row3.createCell(col2);
        c3_1.setCellValue("ARICA");
        c3_1.setCellStyle(cellStyle);
        c3_2.setCellValue("");
        c3_2.setCellStyle(cellStyle);

        // Fila 4 (Encabezado: Nro. de Boletín)
        Row row4 = sheet.createRow(startRow++);
        Cell c4_1 = row4.createCell(col1);
        Cell c4_2 = row4.createCell(col2);
        c4_1.setCellValue("Nro. de Boletín");
        c4_1.setCellStyle(headerStyle);
        c4_2.setCellValue("");
        c4_2.setCellStyle(headerStyle);

        // Fila 5 (Valor del Nro. de Boletín)
        Row row5 = sheet.createRow(startRow++);
        Cell c5_1 = row5.createCell(col1);
        Cell c5_2 = row5.createCell(col2);
        c5_1.setCellValue("112024.0818268009001-0101");
        c5_1.setCellStyle(cellStyle);
        c5_2.setCellValue("");
        c5_2.setCellStyle(cellStyle);

        // (Opcional) Ajustar el ancho de columnas
        sheet.autoSizeColumn(col1);
        sheet.autoSizeColumn(col2);

//        // Aplicar estilo a los rótulos de la derecha
//        for (int r = 0; r <= 5; r++) {
//            Row row = sheet.getRow(r);
//            if (row != null) {
//                Cell labelCell = row.getCell(5);
//                Cell valueCell = row.getCell(6);
//                if (labelCell != null) labelCell.setCellStyle(boldStyle);
//                if (valueCell != null) valueCell.setCellStyle(rightStyle);
//            }
//        }

//        // ----------------------------------------------------------------
//        // 5. Título Central (Fila 6) - fusionado columnas 0..4
//        // ----------------------------------------------------------------
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 4));
//        Row row6 = sheet.createRow(6);
//        Cell titleCell = row6.createCell(0);
//        titleCell.setCellValue("NÓMINA MENSUAL DE RETENCIONES PREVISIONALES");
//        titleCell.setCellStyle(boldStyle);
//
//        // ----------------------------------------------------------------
//        // 6. Nota (Fila 7) - ejemplo
//        // ----------------------------------------------------------------
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 4));
//        Row row7 = sheet.createRow(7);
//        Cell noteCell = row7.createCell(0);
//        noteCell.setCellValue("Nota: La información señalada en las columnas es de carácter referencial.");
//        noteCell.setCellStyle(dataStyle);
//
//        // ----------------------------------------------------------------
//        // 7. Cabecera de la Tabla (Fila 9)
//        // ----------------------------------------------------------------
//        Row headerRow = sheet.createRow(9);
//        String[] headers = {
//                "Corr.",
//                "R.U.T.",
//                "Nombre Beneficiario",
//                "Producto",
//                "Código",
//                "Valor",
//                "Saldo Deuda",
//                "N° Cuotas"
//        };
//        for (int i = 0; i < headers.length; i++) {
//            Cell c = headerRow.createCell(i);
//            c.setCellValue(headers[i]);
//            c.setCellStyle(headerStyle);
//        }
//
//        // ----------------------------------------------------------------
//        // 8. Datos de la Tabla (a partir de Fila 10)
//        // ----------------------------------------------------------------
//        Object[][] data = {
//                {1, "19.870.746-K", "HERRERA DEL REAL MAURICIO ANDRES", "Seguro Vida",   "34104634124", 9346,  9346,  "12/0"},
//                {2, "10.031.407-9", "MUÑOZ GUTIÉRREZ RODRIGO OSVALDO",   "Seguro Vida",   "34104634124", 2278,  2278,  "13/0"},
//                {3, "10.031.407-9", "MUÑOZ GUTIÉRREZ RODRIGO OSVALDO",   "Seguro Vida",   "34104634124", 5445,  32549, "12/0"},
//                {4, "16.088.987-K", "PEÑA YÁÑEZ ÁNGEL ALONSO",           "Crédito Social","34104634124", 3512,  13149, "13/1"},
//                {5, "12.345.678-9", "VEGA ANDRADE MARÍA LUISA",          "Seguro Vida",   "34104634124", 8917,  3496,  "14/2"}
//        };
//
//        int rowNum = 10;
//        double totalAcumulado = 0;
//        for (Object[] fila : data) {
//            Row row = sheet.createRow(rowNum++);
//            for (int col = 0; col < fila.length; col++) {
//                Cell cell = row.createCell(col);
//                if (fila[col] instanceof Number) {
//                    cell.setCellValue(((Number) fila[col]).doubleValue());
//                } else {
//                    cell.setCellValue(fila[col].toString());
//                }
//                cell.setCellStyle(dataStyle);
//            }
//            // Asumimos la columna 5 (Valor) contribuye a un total
//            if (fila[5] instanceof Number) {
//                totalAcumulado += ((Number) fila[5]).doubleValue();
//            }
//        }
//
//        // ----------------------------------------------------------------
//        // 9. Fila de TOTAL (debajo de la tabla)
//        // ----------------------------------------------------------------
//        int totalRowIndex = rowNum;
//        Row totalRow = sheet.createRow(totalRowIndex);
//
//        Cell totalLabelCell = totalRow.createCell(6);
//        totalLabelCell.setCellValue("TOTAL $");
//        totalLabelCell.setCellStyle(boldStyle);
//
//        Cell totalValueCell = totalRow.createCell(7);
//        totalValueCell.setCellValue(totalAcumulado);
//        totalValueCell.setCellStyle(rightStyle);
//
//        // ----------------------------------------------------------------
//        // 10. Observaciones (debajo del total)
//        // ----------------------------------------------------------------
//        int obsRowIndex = totalRowIndex + 2;
//        sheet.addMergedRegion(new CellRangeAddress(obsRowIndex, obsRowIndex + 3, 0, 7));
//        Row obsRow = sheet.createRow(obsRowIndex);
//        Cell obsCell = obsRow.createCell(0);
//        obsCell.setCellValue(
//                "OBSERVACIONES\n\n" +
//                        "Para el descuento y pago de retenciones se considerará:\n" +
//                        "- Retener los valores de acuerdo con la normativa vigente.\n" +
//                        "- ...etc..."
//        );
//        obsCell.setCellStyle(obsStyle);
//        // Ajustamos la altura para texto multilínea
//        obsRow.setHeightInPoints(5 * sheet.getDefaultRowHeightInPoints());
//
//        // ----------------------------------------------------------------
//        // 11. Ajustar Anchos de Columna
//        // ----------------------------------------------------------------
//        for (int i = 0; i < 8; i++) {
//            sheet.autoSizeColumn(i);
//        }
//        sheet.autoSizeColumn(5); // Info derecha
//        sheet.autoSizeColumn(6); // Info derecha

        // 12. Guardar en un flujo de salida
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }


    */


    // ------------------------ ESTILOS BÁSICOS -----------------------

//
//    // Estilo para texto en negrita
//    private CellStyle createBoldStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        style.setFont(font);
//        style.setAlignment(HorizontalAlignment.LEFT);
//        return style;
//    }
//
//    // Estilo para alinear a la derecha (usado en valores)
//    private CellStyle createRightStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        style.setAlignment(HorizontalAlignment.RIGHT);
//        return style;
//    }
//
//    // Estilo para cabeceras de tabla (con bordes y negrita)
//    private CellStyle createHeaderStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        style.setFont(font);
//
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        return style;
//    }
//
//    // Estilo para datos de tabla (con bordes)
//    private CellStyle createDataStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        return style;
//    }
//
//    // Estilo para Observaciones (multilínea, bordes)
//    private CellStyle createObsStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        style.setWrapText(true);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setAlignment(HorizontalAlignment.LEFT);
//        style.setVerticalAlignment(VerticalAlignment.TOP);
//        return style;
//    }
//
//    private CellStyle createTableCellStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        // Bordes
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        // Alineación centrada
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        return style;
//    }

    public ByteArrayOutputStream generarNominaExcel() {

        PayrollFileDTO payrollFileDTO = Data.headPayRollRetentionXmlx();
        List<ListDetailWithholdingDTO> listDetailWithholdingDTOS = Data.detailHoldingXmlx();

        try {
            InputStream is = ExcelExportRetentionService.class.getResourceAsStream("/retencion_credito_modelo.xlsx");
            if (is != null) {
                Workbook workbook = new XSSFWorkbook(is);
                Sheet sheet = workbook.getSheet("RetencionCredito");
                if (sheet == null) {
                    sheet = workbook.getSheetAt(0);
                }

                editDataCellAndRow(sheet, 8, 0, payrollFileDTO.getRazonSocial());
                editDataCellAndRow(sheet, 9, 0, payrollFileDTO.getRut());
                editDataCellAndRow(sheet, 10, 0, payrollFileDTO.getDireccion());
                editDataCellAndRow(sheet, 11, 0, payrollFileDTO.getComuna() + " " + payrollFileDTO.getCiudad());
                editDataCellAndRow(sheet, 9, 8, payrollFileDTO.getPeriodo());
                editDataCellAndRow(sheet, 9, 9, payrollFileDTO.getUltimoDiaPago());
                editDataCellAndRow(sheet, 11, 8, payrollFileDTO.getSucursalPago());
                editDataCellAndRow(sheet, 13, 8, payrollFileDTO.getNumeroBoletin());


                int headerRowIndex = 19;
                addDetalleRows(sheet, listDetailWithholdingDTOS, headerRowIndex);


                int fullPaymentRowIndex = (headerRowIndex + listDetailWithholdingDTOS.size() + 4);
                editDataCellAndRow(sheet, fullPaymentRowIndex, 6, "TOTAL: $");
                editDataCellAndRow(sheet, fullPaymentRowIndex, 7, payrollFileDTO.getTotal());


                List<LineDataDTO> lines = List.of(
                        new LineDataDTO(4, "OBSERVACIONES", false),
                        new LineDataDTO(2, "Para el descuento y pago de nómina de retenciones es conveniente tener presente lo siguiente:", true),
                        new LineDataDTO(2, "Adiciones o rebajas", true),
                        new LineDataDTO(1, "Indicar monto por cancelación de \"ABONOS o TOTAL DEUDA\", o eventuales rebajas en columna habilitada y en la fila del beneficiario que corresponda.", false),
                        new LineDataDTO(2, "En caso de efectuar el pago total de un crédito ya sea por finiquito o anticipado, deberá descontar el monto de las columnas:", false),
                        new LineDataDTO(1, "\"Saldo Deuda + Total descuento al beneficiario.\"", false),
                        new LineDataDTO(2, "Descripciones de las Codificaciones (columna obs.)", true),
                        new LineDataDTO(1, "01.- Dividendo anticipado.", false),
                        new LineDataDTO(1, "02.- Valor a descontar incluye abono.", false),
                        new LineDataDTO(1, "15.- Descuento adicional al deudor.", false),
                        new LineDataDTO(1, "30.- Descuento ambos avales con retención a la empresa.", false),
                        new LineDataDTO(1, "31.- Descuento aval uno con retención a la empresa.", false),
                        new LineDataDTO(1, "32.- Descuento aval dos con retención a la empresa.", false),
                        new LineDataDTO(1, "35.- Descuentos 50% cuota a cada aval.", false),
                        new LineDataDTO(2, "Seguro de Desgravamen", true),
                        new LineDataDTO(1, "Los créditos otorgados por la C.C.A.F. de los Andes se encuentran cubiertos por un seguro de desgravamen, contratado con BICE Vida Compañia de", false),
                        new LineDataDTO(1, "Seguros S.A. póliza DEG-0067, cuyo costo es del 0,045% sobre el saldo de capital mensual de la respectiva obligación. Dicho seguro, de cargo del", false),
                        new LineDataDTO(1, "afiliado, se cobrará mensualmente junto al valor del dividendo y su finalidad es pagar el total del crédito en el evento del fallecimiento del deudor.", false)
                );

                int positionInformation = fullPaymentRowIndex;
                for (LineDataDTO line : lines) {
                    // Sumar el offset indicado para esta línea
                    positionInformation += line.getOffset();
                    // Si además de sumar, se requiere un desplazamiento fijo adicional (por ejemplo, +2) lo aplicas aquí:
                    int targetRow = positionInformation + 2;

                    if (line.getText().equals("OBSERVACIONES")) {
                        editDataCellAndRowBoldAndUnderline(sheet, targetRow, 2, line.getText());
                    } else {
                        if (line.isBoldAndUnderlined()) {
                            editDataCellAndRowBoldAndUnderline(sheet, targetRow, 1, line.getText());
                        } else {
                            editDataCellAndRow(sheet, targetRow, 1, line.getText());
                        }
                    }

                }

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
        if (cellIndex == 6 || cellIndex == 7 || cellIndex == 2) {
            cell.setCellStyle(createCellStyleBold(sheet));
        }
    }

    private void editDataCellAndRowBoldAndUnderline(Sheet sheet, int rowIndex, int cellIndex, String value) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }
        cell.setCellValue(value);
        cell.setCellStyle(createCellStyleBoldAndUnderline(sheet));

    }

    private void addDetalleRows(Sheet sheet, List<ListDetailWithholdingDTO> detalles, int headerRowIndex) {
        // La primera fila de datos será la fila inmediatamente después del encabezado
        int currentRowIndex = headerRowIndex;

        for (ListDetailWithholdingDTO detalle : detalles) {
            Row row = sheet.createRow(currentRowIndex++);

            int colIndex = 0;

            // Columna 0: Corr.
            Cell cellCorr = row.createCell(colIndex++);
            cellCorr.setCellValue(detalle.getCorr());
            cellCorr.setCellStyle(createCellStyleListDetailText(sheet));

            // Columna 1: R.U.T. Beneficiario
            Cell cellRut = row.createCell(colIndex++);
            cellRut.setCellValue(detalle.getRutBeneficiario());
            cellRut.setCellStyle(createCellStyleListDetailText(sheet));

            // Columna 2: Nombre Beneficiario
            Cell cellNombre = row.createCell(colIndex++);
            cellNombre.setCellValue(detalle.getNombreBeneficiario());

            // Columna 3: Producto
            Cell cellProducto = row.createCell(colIndex++);
            cellProducto.setCellValue(detalle.getProducto());

            // Columna 4: Código
            Cell cellCodigo = row.createCell(colIndex++);
            cellCodigo.setCellValue(detalle.getCodigo());
            cellCodigo.setCellStyle(createCellStyleListDetailText(sheet));

            // Columna 5: Cuota
            Cell cellCuota = row.createCell(colIndex++);
            cellCuota.setCellValue(detalle.getCuota());
            cellCuota.setCellStyle(createCellStyleListDetailText(sheet));

            // Columna 6: Valor a Descontar
            Cell cellValorDesc = row.createCell(colIndex++);
            cellValorDesc.setCellValue(detalle.getValorDescontar());
            cellValorDesc.setCellStyle(createCellStyleListDetailText(sheet));

            // Columna 7: Total Desc. Al Beneficiario
            Cell cellTotalDesc = row.createCell(colIndex++);
            cellTotalDesc.setCellValue(detalle.getTotalDescuentoBeneficiario());
            cellTotalDesc.setCellStyle(createCellStyleListDetailText(sheet));

            // Columna 8: Saldo Deuda
            Cell cellSaldoDeuda = row.createCell(colIndex++);
            cellSaldoDeuda.setCellValue("---------");
            cellSaldoDeuda.setCellStyle(createCellStyleListDetailLine(sheet));

            // Columna 9: Valor a Descontar (Adicional)
            Cell cellValorAdic = row.createCell(colIndex++);
            cellValorAdic.setCellValue("---------");
            cellValorAdic.setCellStyle(createCellStyleListDetailLine(sheet));

            // Columna 10: Obs.
            Cell cellObs = row.createCell(colIndex++);
            cellObs.setCellValue(" ");
        }
    }

    // ------------------------ ESTILOS BÁSICOS -----------------------
    private CellStyle createCellStyleListDetailText(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        return style;
    }

    private CellStyle createCellStyleBold(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.TOP);

        return style;
    }

    private CellStyle createCellStyleBoldAndUnderline(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setUnderline(Font.U_SINGLE);
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        return style;
    }

    private CellStyle createCellStyleListDetailLine(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();
        CellStyle style = workbook.createCellStyle();
        // Alineación centrada
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
}
