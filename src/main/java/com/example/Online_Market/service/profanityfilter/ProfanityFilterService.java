package com.example.Online_Market.service.profanityfilter;

import com.example.Online_Market.dto.ProfanityResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service("profanityFilterService")
public class ProfanityFilterService {

    @Value("${x.api.key}")
    private String apiKey;

    private final WebClient profanityWebClient;

    public ProfanityFilterService(
            WebClient profanityWebClient
    ){
        this.profanityWebClient = profanityWebClient;
    }

    public Mono<ProfanityResponseDto> filterText(String textToFilter){
        return profanityWebClient
                .get()
                .uri(uriBuilder -> {
                                uriBuilder.path("/v1/profanityfilter")
                                        .queryParam("text",textToFilter);
                                return uriBuilder.build();
                            })
                .retrieve()
                .bodyToMono(ProfanityResponseDto.class);
    }
}
