package com.example.Online_Market.service.profanityfilter;

import com.example.Online_Market.dto.ProfanityResponseDto;
import com.example.Online_Market.exception.BadWordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

@Service("profanityFilterService")
@Slf4j
public class ProfanityFilterService {

    private final WebClient profanityWebClient;

    public ProfanityFilterService(
            WebClient profanityWebClient
    ){
        this.profanityWebClient = profanityWebClient;
    }

    private Mono<ProfanityResponseDto> profanityResponse(String textToFilter){
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

    private void hasBadWords(Mono<ProfanityResponseDto> response){
        ProfanityResponseDto responseDto = response.block();
        if(responseDto==null){
            throw new NullPointerException("Profanity API returned null!");
        }

        if(responseDto.hasProfanity){
            throw new BadWordException("Message contains bad words!");
        }

    }

    public void hasBadWords(String textTOCheck){
        hasBadWords(profanityResponse(textTOCheck));
    }
}
