package com.myapp.estoque.dto;

import com.myapp.estoque.enums.TipoMovimentacao;
import com.myapp.estoque.model.Produto;

import java.time.LocalDateTime;

public class ControleEstoqueResponseDTO {
    private TipoMovimentacao tipoMovimentacao;
    private double quantidade;
    private LocalDateTime data_hora;
    private String observacao;
    private Produto produto;
}
