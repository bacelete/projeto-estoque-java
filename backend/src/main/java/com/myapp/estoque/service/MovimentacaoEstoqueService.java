package com.myapp.estoque.service;

import com.myapp.estoque.model.MovimentacaoEstoque;
import com.myapp.estoque.repository.MovimentacaoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoEstoqueService {
    @Autowired
    MovimentacaoEstoqueRepository movimentacaoRepository;

    public MovimentacaoEstoque registrarMovimentacao(MovimentacaoEstoque movimentacao) {
        return movimentacaoRepository.save(movimentacao);
    }

    public boolean isLower(int qtdProduto, int qtdSolicitada) {
        return qtdProduto < qtdSolicitada;
    }
}
