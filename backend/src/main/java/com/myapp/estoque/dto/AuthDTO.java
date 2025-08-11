package com.myapp.estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AuthDTO {
    private String login;
    private String password;
}
