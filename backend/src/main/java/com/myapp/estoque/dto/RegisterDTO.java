package com.myapp.estoque.dto;

import com.myapp.estoque.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {
    private String login;
    private String password;
    private TipoUsuario role;
}
