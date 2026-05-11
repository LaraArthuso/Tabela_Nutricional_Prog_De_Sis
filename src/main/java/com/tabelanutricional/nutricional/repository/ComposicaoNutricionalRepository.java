package com.tabelanutricional.nutricional.repository;

import com.tabelanutricional.nutricional.entity.ComposicaoNutricional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComposicaoNutricionalRepository extends JpaRepository<ComposicaoNutricional, Long> {
}