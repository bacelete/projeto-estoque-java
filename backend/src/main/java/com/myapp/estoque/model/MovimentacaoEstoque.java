package com.myapp.estoque.model;

import com.myapp.estoque.enums.TipoMovimentacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class MovimentacaoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo_movimentacao;

    @PositiveOrZero
    @Max(value = 100)
    private int quantidade;

    private LocalDateTime data_hora;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    public MovimentacaoEstoque() {}

}
