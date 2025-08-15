package com.myapp.estoque.service;

import com.myapp.estoque.model.ControleEstoque;
import com.myapp.estoque.repository.ControleEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControleEstoqueService {
    @Autowired
    ControleEstoqueRepository movimentacaoRepository;

    public ControleEstoque registrarMovimentacao(ControleEstoque movimentacao) {
        return movimentacaoRepository.save(movimentacao);
    }

    public boolean isLower(int qtdProduto, int qtdSolicitada) {
        return qtdProduto < qtdSolicitada;
    }

    public List<ControleEstoque> findAll() { return movimentacaoRepository.findAll(); }
}
