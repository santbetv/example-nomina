package com.sbvdeveloper.poc_nomina.config;

import cl.ccla.app.lib_core_pdf_generator.strategy.ReportStrategy;
import cl.ccla.app.lib_core_pdf_generator.strategy.ReportStrategyContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfig {
    @Bean
    public ReportStrategy reportStrategy() {
        return new ReportStrategyContext();
    }
}
