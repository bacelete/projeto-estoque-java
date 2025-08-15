package com.myapp.estoque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank
    @Size(max = 100, message= "Tamanho máximo do nome deve ser de 100 caracteres.")
    private String nome;

    @NotNull
    @PositiveOrZero(message = "Quantidade deve ser positiva!")
    private int quantidade;

    @NotNull
    @PositiveOrZero(message = "Valor deve ser positivo!")
    private double preco;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        this.dataCadastro  = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "produto")
    @JsonIgnore
    private List<ControleEstoque> movimentacoes;

    public Produto() {}
}
