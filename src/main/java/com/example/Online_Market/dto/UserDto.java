package com.example.Online_Market.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 2, max = 15)
    @NotBlank
    private String firstName;

    @NotNull
    @Size(min = 5, max = 15)
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String email;


    @NotNull
    @NotBlank
    private String phone;


    @NotNull
    @NotBlank
    private String country;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String password;

    @NotBlank
    @NotNull
    private String role;

}
