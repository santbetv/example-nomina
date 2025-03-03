package com.sbvdeveloper.poc_nomina.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LoadFilesConfig {

    @Bean
    public Map<String, String> load() {
        var loadPdfRetention = String.format("%s, %s, %s", "nominas_retenciones.jasper", "nominas_retenciones_hoja2.jasper", "nominas_retenciones_hoja3.jasper");
        Map<String, String> templateMap = new HashMap<>();
        templateMap.put("pdfNomina", loadPdfRetention);

        return templateMap;
    }
}
