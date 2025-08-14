package com.myapp.estoque.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimentacaoEstoqueDTO {
    private int quantidade;
    private String observacao;
}
