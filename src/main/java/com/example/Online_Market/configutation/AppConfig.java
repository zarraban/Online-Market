package com.example.Online_Market.configutation;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.example.Online_Market")
public class AppConfig {
    // Layout dialect Bean for proper work of Thymeleaf Layout Dialect
    @Bean
    public LayoutDialect layoutDialect(){
        return new LayoutDialect();
    }
}
