package com.example.Online_Market.service.profanityfilter;

import com.example.Online_Market.dto.ProfanityResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    private boolean hasBadWords(Mono<ProfanityResponseDto> response){
        if(response == null){
            log.error("Response object in hasBadWords() is null! ");
            throw new NullPointerException("Response object is null");
        }
        if(Objects.requireNonNull(response.block()).hasProfanity){
            return true;
        } else {
            return false;
        }
    }

    public boolean hasBadWords(String textTOCheck){
        return hasBadWords(profanityResponse(textTOCheck));
    }
}
