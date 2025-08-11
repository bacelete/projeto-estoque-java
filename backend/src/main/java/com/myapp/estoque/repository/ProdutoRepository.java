package com.myapp.estoque.repository;

import com.myapp.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Produto findByNome(String nome);
    List<Produto> findByFornecedorId(Integer id);
    List<Produto> findByCategoriaId(Integer id);
}
