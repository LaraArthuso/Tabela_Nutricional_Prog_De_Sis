package com.tabelanutricional.nutricional.controller;

import com.tabelanutricional.nutricional.entity.Alimento;
import com.tabelanutricional.nutricional.service.AlimentoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alimentos")
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    @GetMapping
    public List<Alimento> listarTodos() {
        return alimentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Alimento buscarPorId(@PathVariable Long id) {
        return alimentoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alimento cadastrar(@RequestBody Alimento alimento) {
        return alimentoService.cadastrar(alimento);
    }

    @PutMapping("/{id}")
    public Alimento atualizar(@PathVariable Long id, @RequestBody Alimento alimento) {
        return alimentoService.atualizar(id, alimento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        alimentoService.remover(id);
    }
}