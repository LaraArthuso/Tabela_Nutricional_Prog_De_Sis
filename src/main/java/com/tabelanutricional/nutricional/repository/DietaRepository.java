package com.tabelanutricional.nutricional.repository;

import com.tabelanutricional.nutricional.entity.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietaRepository extends JpaRepository<Dieta, Long> {
}