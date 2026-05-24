package com.tabelanutricional.nutricional.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tabelanutricional.nutricional.dto.RelatorioNutricionalDTO;
import com.tabelanutricional.nutricional.service.RelatorioService;

@RestController
@RequestMapping("/api/relatorio")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/paciente/{id}")
    public RelatorioNutricionalDTO gerarRelatorio(@PathVariable Long id) {
        return relatorioService.gerarRelatorio(id);
    }
}
