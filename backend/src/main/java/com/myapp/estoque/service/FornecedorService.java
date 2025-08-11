package com.myapp.estoque.service;

import com.myapp.estoque.model.Fornecedor;
import com.myapp.estoque.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    public boolean isEmpty(int id) { return !fornecedorRepository.existsById(id); }
    public Fornecedor criarFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }
    public Optional<Fornecedor> buscarPeloId(int id) {
        return fornecedorRepository.findById(id);
    }
    public void deletarFornecedorPeloId(int id) {
        fornecedorRepository.deleteById(id);
    }
    public Fornecedor buscarPeloCnpj(String cnpj) { return fornecedorRepository.findByCnpj(cnpj); }
    public List<Fornecedor> buscarTodos() { return fornecedorRepository.findAll(); }

}
