package com.myapp.estoque.service;

import com.myapp.estoque.model.Produto;
import com.myapp.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvarProduto(Produto p) {
        return produtoRepository.save(p);
    }
    public Optional<Produto> buscarPorId(int id) {
        return produtoRepository.findById(id);
    }
    public Produto buscarPeloNome(String nome) {
        return produtoRepository.findByNome(nome);
    }
    public boolean isEmpty(int id) {  return !produtoRepository.existsById(id); }
    public boolean isAllEmpty() { return produtoRepository.findAll().isEmpty(); }
    public void deletarPeloId(int id) {
        produtoRepository.deleteById(id);
    }
    public List<Produto> buscarProdutosPeloFornecedor(int id) { return produtoRepository.findByFornecedorId(id); }
    public List<Produto> buscarProdutosPelaCategoria(int id) { return produtoRepository.findByCategoriaId(id); }
    public List<Produto> buscarTudo() { return produtoRepository.findAll(); }

}
