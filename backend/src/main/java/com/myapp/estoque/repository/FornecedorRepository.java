package com.myapp.estoque.repository;

import com.myapp.estoque.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Fornecedor findByCnpj(String cnpj);
}
