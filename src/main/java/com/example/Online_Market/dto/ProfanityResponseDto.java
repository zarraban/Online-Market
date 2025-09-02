package com.example.Online_Market.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ProfanityResponseDto {
    public String original;
    public String censored;
    public Boolean hasProfanity;
}
