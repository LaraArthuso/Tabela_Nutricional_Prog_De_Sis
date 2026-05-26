package com.tabelanutricional.nutricional.repository;

import com.tabelanutricional.nutricional.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}