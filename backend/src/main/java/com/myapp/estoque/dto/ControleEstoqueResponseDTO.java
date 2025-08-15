package com.myapp.estoque.dto;

import com.myapp.estoque.enums.TipoMovimentacao;
import com.myapp.estoque.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ControleEstoqueResponseDTO {
    private TipoMovimentacao tipo_movimentacao;
    private double quantidade;
    private LocalDateTime data_hora;
    private String observacao;
    private String nome_produto;

    public ControleEstoqueResponseDTO() {}
}
