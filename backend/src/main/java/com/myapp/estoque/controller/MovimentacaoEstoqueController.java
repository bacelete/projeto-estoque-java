package com.myapp.estoque.controller;

import com.myapp.estoque.dto.ControleEstoqueDTO;
import com.myapp.estoque.exception.EmptyObjectException;
import com.myapp.estoque.exception.NotEnoughException;
import com.myapp.estoque.model.MovimentacaoEstoque;
import com.myapp.estoque.model.Produto;
import com.myapp.estoque.enums.TipoMovimentacao;
import com.myapp.estoque.service.MovimentacaoEstoqueService;
import com.myapp.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/estoque")
public class MovimentacaoEstoqueController {
    @Autowired
    private MovimentacaoEstoqueService movimentacaoService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/produto/{id}/adicionar")
    public ResponseEntity<MovimentacaoEstoque> addQuantidadeProduto(@PathVariable int id, @RequestBody ControleEstoqueDTO dto) {
        if (produtoService.isEmpty(id)) {
            throw new EmptyObjectException("Produto não encontrado.");
        }

        Produto produto = produtoService.buscarPorId(id).get();
        produto.setQuantidade(produto.getQuantidade() + dto.getQuantidade());
        produtoService.salvarProduto(produto);

        MovimentacaoEstoque nova = new MovimentacaoEstoque();
        nova.setProduto(produto);
        nova.setData_hora(LocalDateTime.now());
        nova.setTipo_movimentacao(TipoMovimentacao.ENTRADA);

        nova.setObservacao(dto.getObservacao()); //inspecionar isso aqui dps;
        nova.setQuantidade(dto.getQuantidade());

        return ResponseEntity.ok(movimentacaoService.registrarMovimentacao(nova));
    }

    @PostMapping("/produto/{id}/remover")
    public ResponseEntity<MovimentacaoEstoque> removerQuantidadeProduto(@PathVariable int id, @RequestBody ControleEstoqueDTO dto) {
        if (produtoService.isEmpty(id)) {
            throw new EmptyObjectException("Produto não encontrado.");
        }

        Produto produto = produtoService.buscarPorId(id).get();

        if (movimentacaoService.isLower(produto.getQuantidade(), dto.getQuantidade())) {
            throw new NotEnoughException();
        }

        produto.setQuantidade(produto.getQuantidade() - dto.getQuantidade());
        produtoService.salvarProduto(produto);

        MovimentacaoEstoque nova = new MovimentacaoEstoque();
        nova.setProduto(produto);
        nova.setData_hora(LocalDateTime.now());
        nova.setTipo_movimentacao(TipoMovimentacao.SAIDA);

        nova.setObservacao(dto.getObservacao()); //inspecionar isso aqui dps;
        nova.setQuantidade(dto.getQuantidade());

        return ResponseEntity.ok(movimentacaoService.registrarMovimentacao(nova));
    }




}
