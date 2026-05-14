package com.tabelanutricional.nutricional.controller;

import com.tabelanutricional.nutricional.entity.Dieta;
import com.tabelanutricional.nutricional.service.DietaService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dietas")
public class DietaController {

    private final DietaService dietaService;

    public DietaController(DietaService dietaService) {
        this.dietaService = dietaService;
    }

    @GetMapping
    public List<Dieta> listarTodos() {
        return dietaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Dieta buscarPorId(@PathVariable Long id) {
        return dietaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dieta cadastrar(@RequestBody Dieta dieta) {
        return dietaService.salvar(dieta);
    }

    @PutMapping("/{id}")
    public Dieta atualizar(@PathVariable Long id, @RequestBody Dieta dieta) {
        return dietaService.atualizar(id, dieta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        dietaService.remover(id);
    }
}
