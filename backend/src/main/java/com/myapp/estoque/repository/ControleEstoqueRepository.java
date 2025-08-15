package com.myapp.estoque.repository;

import com.myapp.estoque.model.ControleEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ControleEstoqueRepository extends JpaRepository<ControleEstoque, Integer> {
}
