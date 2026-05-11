package com.tabelanutricional.nutricional.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class ComposicaoNutricional {

    @Id
    @GeneratedValue
    private Long id;

    private Double quantidade;

    @ManyToOne
    @JoinColumn(name = "refeicao_id")
    private Refeicao refeicao;

    public Refeicao getRefeicao() {
    return refeicao;
}

public void setRefeicao(Refeicao refeicao) {
    this.refeicao = refeicao;
}
    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private Alimento alimento;

public Alimento getAlimento() {
    return alimento;
}

public void setAlimento(Alimento alimento) {
    this.alimento = alimento;
}

    public ComposicaoNutricional() {
    }

    public Long getId() {
        return id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}