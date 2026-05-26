package com.tabelanutricional.nutricional.controller;

import com.tabelanutricional.nutricional.model.ComposicaoNutricional;
import com.tabelanutricional.nutricional.service.ComposicaoNutricionalService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/composicoes")
public class ComposicaoNutricionalController {

    private final ComposicaoNutricionalService composicaoNutricionalService;

    public ComposicaoNutricionalController(ComposicaoNutricionalService composicaoNutricionalService) {
        this.composicaoNutricionalService = composicaoNutricionalService;
    }

    @GetMapping
    public List<ComposicaoNutricional> listarTodos() {
        return composicaoNutricionalService.listarTodos();
    }

    @GetMapping("/{id}")
    public ComposicaoNutricional buscarPorId(@PathVariable Long id) {
        return composicaoNutricionalService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComposicaoNutricional cadastrar(@RequestBody ComposicaoNutricional composicaoNutricional) {
        return composicaoNutricionalService.salvar(composicaoNutricional);
    }

    @PutMapping("/{id}")
    public ComposicaoNutricional atualizar(@PathVariable Long id, @RequestBody ComposicaoNutricional composicaoNutricional) {
        return composicaoNutricionalService.atualizar(id, composicaoNutricional);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        composicaoNutricionalService.remover(id);
    }
}
