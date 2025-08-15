package com.myapp.estoque.controller;

import com.myapp.estoque.dto.ControleEstoqueDTO;
import com.myapp.estoque.dto.ControleEstoqueResponseDTO;
import com.myapp.estoque.exception.EmptyObjectException;
import com.myapp.estoque.exception.NotEnoughException;
import com.myapp.estoque.model.ControleEstoque;
import com.myapp.estoque.model.Produto;
import com.myapp.estoque.enums.TipoMovimentacao;
import com.myapp.estoque.service.ControleEstoqueService;
import com.myapp.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ControleEstoqueController {
    @Autowired
    private ControleEstoqueService controleEstoqueService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/produto/{id}/adicionar")
    public ResponseEntity<ControleEstoque> addQuantidadeProduto(@PathVariable int id, @RequestBody ControleEstoqueDTO dto) {
        if (produtoService.isEmpty(id)) {
            throw new EmptyObjectException("Produto não encontrado.");
        }

        Produto produto = produtoService.buscarPorId(id).get();
        produto.setQuantidade(produto.getQuantidade() + dto.getQuantidade());
        produtoService.salvarProduto(produto);

        ControleEstoque nova = new ControleEstoque();
        nova.setProduto(produto);
        nova.setData_hora(LocalDateTime.now());
        nova.setTipo_movimentacao(TipoMovimentacao.ENTRADA);

        nova.setObservacao(dto.getObservacao()); //inspecionar isso aqui dps;
        nova.setQuantidade(dto.getQuantidade());

        return ResponseEntity.ok(controleEstoqueService.registrarMovimentacao(nova));
    }

    @PostMapping("/produto/{id}/remover")
    public ResponseEntity<ControleEstoque> removerQuantidadeProduto(@PathVariable int id, @RequestBody ControleEstoqueDTO dto) {
        if (produtoService.isEmpty(id)) {
            throw new EmptyObjectException("Produto não encontrado.");
        }

        Produto produto = produtoService.buscarPorId(id).get();

        if (controleEstoqueService.isLower(produto.getQuantidade(), dto.getQuantidade())) {
            throw new NotEnoughException();
        }

        produto.setQuantidade(produto.getQuantidade() - dto.getQuantidade());
        produtoService.salvarProduto(produto);

        ControleEstoque nova = new ControleEstoque();
        nova.setProduto(produto);
        nova.setData_hora(LocalDateTime.now());
        nova.setTipo_movimentacao(TipoMovimentacao.SAIDA);

        nova.setObservacao(dto.getObservacao()); //inspecionar isso aqui dps;
        nova.setQuantidade(dto.getQuantidade());

        return ResponseEntity.ok(controleEstoqueService.registrarMovimentacao(nova));
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<ControleEstoqueResponseDTO>> getRelatorio() {
        if (controleEstoqueService.findAll().isEmpty()) {
            throw new EmptyObjectException("Não há movimentações registradas no estoque.");
        }

        List<ControleEstoque> relatorios = controleEstoqueService.findAll();
        List<ControleEstoqueResponseDTO> relatoriosDTO = new ArrayList<>();

        for (ControleEstoque relatorio : relatorios) {
            relatoriosDTO.add(new ControleEstoqueResponseDTO(relatorio.getTipo_movimentacao(),
                    relatorio.getQuantidade(), relatorio.getData_hora(), relatorio.getObservacao(),
                    relatorio.getProduto().getNome()));
        }

        return ResponseEntity.ok(relatoriosDTO);
    }

    @DeleteMapping("/relatorio/{id}")
    public ResponseEntity<?> deleteRelatorio(@PathVariable int id) {
        if (controleEstoqueService.findById(id).isEmpty()) {
            throw new EmptyObjectException("Relatório não encontrado!");
        }
        controleEstoqueService.delete(id);
        return ResponseEntity.ok("Relatório excluído com sucesso!");
    }



}
