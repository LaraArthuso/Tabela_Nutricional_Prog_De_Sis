package com.tabelanutricional.nutricional.controller;

import com.tabelanutricional.nutricional.entity.Refeicao;
import com.tabelanutricional.nutricional.service.RefeicaoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refeicoes")
public class RefeicaoController {

    private final RefeicaoService refeicaoService;

    public RefeicaoController(RefeicaoService refeicaoService) {
        this.refeicaoService = refeicaoService;
    }

    @GetMapping
    public List<Refeicao> listarTodos() {
        return refeicaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Refeicao buscarPorId(@PathVariable Long id) {
        return refeicaoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Refeicao cadastrar(@RequestBody Refeicao refeicao) {
        return refeicaoService.cadastrar(refeicao);
    }

    @PutMapping("/{id}")
    public Refeicao atualizar(@PathVariable Long id, @RequestBody Refeicao refeicao) {
        return refeicaoService.atualizar(id, refeicao);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        refeicaoService.remover(id);
    }
}