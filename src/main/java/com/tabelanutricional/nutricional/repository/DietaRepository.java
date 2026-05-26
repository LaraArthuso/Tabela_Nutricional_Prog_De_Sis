package com.tabelanutricional.nutricional.repository;

import com.tabelanutricional.nutricional.model.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DietaRepository extends JpaRepository<Dieta, Long> {

    // Spring Data gera automaticamente: SELECT * FROM dieta WHERE paciente_id = ?
    List<Dieta> findByPacienteId(Long pacienteId);
}