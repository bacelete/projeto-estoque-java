package com.myapp.estoque.service;

import com.myapp.estoque.model.Categoria;
import com.myapp.estoque.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria criar(Categoria categoria) { return categoriaRepository.save(categoria); }
    public Optional<Categoria> buscarPeloId(int id) { return categoriaRepository.findById(id); }
    public void deletarPeloId(int id) { categoriaRepository.deleteById(id); }
    public Categoria buscarPeloNome(String nome) { return categoriaRepository.findByNome(nome); }
    public boolean isEmpty(int id) { return !categoriaRepository.existsById(id); }
    public List<Categoria> buscarTodas() { return categoriaRepository.findAll(); }

}
