package com.tabelanutricional.nutricional.repository;

import com.tabelanutricional.nutricional.model.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {

    // Spring Data gera automaticamente: SELECT * FROM refeicao WHERE paciente_id = ?
    List<Refeicao> findByPacienteId(Long pacienteId);
}