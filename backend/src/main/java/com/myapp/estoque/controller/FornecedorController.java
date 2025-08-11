package com.myapp.estoque.controller;

import com.myapp.estoque.exception.EmptyObjectException;
import com.myapp.estoque.model.Categoria;
import com.myapp.estoque.model.Fornecedor;
import com.myapp.estoque.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {
    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping("/criar")
    public ResponseEntity<Fornecedor> criarFornecedor(@RequestBody Fornecedor fornecedor) {
        Fornecedor novo = fornecedorService.criarFornecedor(fornecedor);
        return ResponseEntity.created(URI.create("/fornecedor/id" + novo.getId()))
                .body(novo);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable int id) {
        Fornecedor fornecedor = fornecedorService.buscarPeloId(id)
                .orElseThrow(() -> new EmptyObjectException("Fornecedor não encontrado."));

        return ResponseEntity.ok(fornecedor);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Fornecedor> getFornecedorByCnpj(@RequestParam String cnpj) {
        Fornecedor fornecedor = fornecedorService.buscarPeloCnpj(cnpj);

        if (fornecedor == null) {
            throw new EmptyObjectException("Fornecedor não encontrado.");
        }

        return ResponseEntity.ok(fornecedor);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Fornecedor>> getAllFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.buscarTodos();

        if (fornecedores.isEmpty()) {
            throw new EmptyObjectException("Fornecedores indisponíveis.");
        }

        return ResponseEntity.ok(fornecedores);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteFornecedor(@PathVariable int id) {
        if (fornecedorService.isEmpty(id)) {
            throw new EmptyObjectException("Fornecedor não encontrado.");
        }

        fornecedorService.deletarFornecedorPeloId(id);
        return ResponseEntity.ok("Fornecedor excluído com sucesso!");
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Fornecedor> editarFornecedor(@PathVariable int id, @RequestBody Fornecedor novo) {
        if (fornecedorService.isEmpty(id)) {
            throw new EmptyObjectException("Fornecedor não encontrado.");
        }

        Fornecedor fornecedor = fornecedorService.buscarPeloId(id).get();
        fornecedor.setNome(novo.getNome());
        fornecedor.setEndereco(novo.getEndereco());
        fornecedor.setCnpj(novo.getCnpj());

        Fornecedor atualizado = fornecedorService.criarFornecedor(fornecedor);
        return ResponseEntity.ok(atualizado);
    }

}
