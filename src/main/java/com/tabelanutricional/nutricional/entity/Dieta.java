package com.tabelanutricional.nutricional.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Dieta {

    @Id
    @GeneratedValue
    private Long id;

  
    private String descricao;


@ManyToOne
@JoinColumn(name = "paciente_id")
private Paciente paciente;

public Paciente getPaciente() {
    return paciente;
}

public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
}

    public Dieta() {
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}