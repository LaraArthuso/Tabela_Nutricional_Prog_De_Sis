package com.tabelanutricional.nutricional.repository;

import com.tabelanutricional.nutricional.entity.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {

    // Spring Data gera automaticamente: SELECT * FROM refeicao WHERE paciente_id = ?
    List<Refeicao> findByPacienteId(Long pacienteId);
}