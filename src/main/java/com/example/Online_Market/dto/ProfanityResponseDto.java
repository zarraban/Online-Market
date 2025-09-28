package com.example.Online_Market.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfanityResponseDto {
    public String original;
    public String censored;
    @JsonProperty("has_profanity")
    public Boolean hasProfanity;

    @Override
    public String toString() {
        return "ProfanityResponseDto{" +
                "original='" + original + '\'' +
                ", censored='" + censored + '\'' +
                ", hasProfanity=" + hasProfanity +
                '}';
    }
}
