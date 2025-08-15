package com.myapp.estoque.service;

import com.myapp.estoque.model.ControleEstoque;
import com.myapp.estoque.repository.ControleEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ControleEstoqueService {
    @Autowired
    ControleEstoqueRepository controleEstoqueRepository;

    public ControleEstoque registrarMovimentacao(ControleEstoque movimentacao) { return controleEstoqueRepository.save(movimentacao); }
    public boolean isLower(int qtdProduto, int qtdSolicitada) { return qtdProduto < qtdSolicitada; }
    public List<ControleEstoque> findAll() { return controleEstoqueRepository.findAll(); }
    public Optional<ControleEstoque> findById(int id) { return controleEstoqueRepository.findById(id); }
    public void delete(int id) { controleEstoqueRepository.deleteById(id);}
}
