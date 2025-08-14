package com.myapp.estoque.controller;

import com.myapp.estoque.dto.ProdutoDTO;
import com.myapp.estoque.exception.EmptyObjectException;
import com.myapp.estoque.model.Categoria;
import com.myapp.estoque.model.Fornecedor;
import com.myapp.estoque.model.Produto;
import com.myapp.estoque.service.CategoriaService;
import com.myapp.estoque.service.FornecedorService;
import com.myapp.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private CategoriaService categoriaService;


    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody ProdutoDTO produtoDTO) {

        Categoria categoria = produtoDTO.getCategoria();
        Fornecedor fornecedor = produtoDTO.getFornecedor();

        if (categoriaService.isEmpty(categoria.getId()) || fornecedorService.isEmpty(fornecedor.getId())) {
            throw new EmptyObjectException("Categoria e/ou fornecedor não encontrado.");
        }

        Produto novo = new Produto();
        novo.setQuantidade(produtoDTO.getQuantidade());
        novo.setNome(produtoDTO.getNome());
        novo.setCategoria(categoria);
        novo.setFornecedor(fornecedor);

        produtoService.salvarProduto(novo);

        return ResponseEntity
                .ok(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable int id) {
        Produto produto = produtoService.buscarPorId(id)
                .orElseThrow(() -> new EmptyObjectException("Produto não encontrado."));

        return ResponseEntity.ok(produto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Produto> getProdutoByNome(@RequestParam String nome) {
        Produto produto = produtoService.buscarPeloNome(nome);

        if (produto == null) {
            throw new EmptyObjectException("Produto não encontrado.");
        }

        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        if (produtoService.isEmpty(id)) {
            throw new EmptyObjectException("Produto não encontrado.");
        }
        produtoService.deletarPeloId(id);
        return ResponseEntity.ok("Produto excluído com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable int id, @RequestBody ProdutoDTO novo) {
        if (produtoService.isEmpty(id)) {
            throw new EmptyObjectException("Produto não encontrado.");
        }

        Produto produto = produtoService.buscarPorId(id).get();

        var newCategoria = categoriaService.buscarPeloId(novo.getCategoria().getId());
        var newFornecedor = fornecedorService.buscarPeloId(novo.getFornecedor().getId());

        if (newCategoria.isEmpty() || newFornecedor.isEmpty()) {
            throw new EmptyObjectException("Categoria e/ou fornecedor não encontrado(s).");
        }

        produto.setNome(novo.getNome());
        produto.setQuantidade(novo.getQuantidade());
        produto.setPreco(novo.getPreco());
        produto.setCategoria(newCategoria.get());
        produto.setFornecedor(newFornecedor.get());
        produto.setDataCadastro(LocalDateTime.now());

        Produto atualizado = produtoService.salvarProduto(produto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/fornecedor/{id}")
    public ResponseEntity<List<Produto>> getProdutosByFornecedor(@PathVariable int id) {
        if (fornecedorService.isEmpty(id)) {
            throw new EmptyObjectException("Fornecedor não encontrado.");
        }

        List<Produto> produtosFornecedor = produtoService.buscarProdutosPeloFornecedor(id);

        if (produtosFornecedor.isEmpty()) {
            throw new EmptyObjectException("Produtos não encontrados.");
        }

        return ResponseEntity.ok(produtosFornecedor);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Produto>> getProdutosByCategoria(@PathVariable int id) {
        if (categoriaService.isEmpty(id)) {
            throw new EmptyObjectException("Categoria não encontrada.");
        }

        List<Produto> produtosByCategoria = produtoService.buscarProdutosPelaCategoria(id);

        if (produtosByCategoria.isEmpty()) {
            throw new EmptyObjectException("Produtos não encontrados.");
        }

        return ResponseEntity.ok(produtosByCategoria);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Produto>> getAllProdutos() {
        if (produtoService.isAllEmpty()) {
            throw new EmptyObjectException("Produtos não encontrados.");
        }

        List<Produto> produtos = produtoService.buscarTudo();
        return ResponseEntity.ok(produtos);
    }


}
