package com.myapp.estoque.dto;

import com.myapp.estoque.model.Categoria;
import com.myapp.estoque.model.Fornecedor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoDTO {
    private String nome;
    private double preco;
    private int quantidade;
    private Categoria categoria;
    private Fornecedor fornecedor;
}
