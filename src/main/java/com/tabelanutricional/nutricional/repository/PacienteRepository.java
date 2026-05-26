package com.tabelanutricional.nutricional.repository;

import com.tabelanutricional.nutricional.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}