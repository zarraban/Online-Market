package com.example.Online_Market.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @NotNull
    @NotBlank
    @Size(min = 5, max = 100, message = "Size of your text must be from 5 to 100 chars!")
    private String text;

    @NotNull
    @NotBlank
    private Boolean isAnonymous;
}
