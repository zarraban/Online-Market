package com.example.Online_Market.configutation;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(value = "com.example.Online_Market")
public class AppConfig {

    @Value("${ninja.api.base-url}")
    private String baseUrlNinjaApi;

    @Value("${x.api.key}")
    private String xApiKey;


    // Layout dialect Bean for proper work of Thymeleaf Layout Dialect
    @Bean
    public LayoutDialect layoutDialect(){
        return new LayoutDialect();
    }

    @Bean
    public WebClient api(WebClient.Builder builder){
        return builder
                .baseUrl(baseUrlNinjaApi)
                .defaultHeader("X-Api-Key", xApiKey)
                .build();
    }
}
