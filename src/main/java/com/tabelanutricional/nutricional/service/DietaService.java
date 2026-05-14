package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.Dieta;
import com.tabelanutricional.nutricional.repository.DietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DietaService {

    @Autowired
    private DietaRepository dietaRepository;

    public List<Dieta> listarTodos() {
        return dietaRepository.findAll();
    }

    public Dieta buscarPorId(Long id) {
        return localizarPorId(id);
    }

    public Dieta salvar(Dieta dieta) {
        return dietaRepository.save(dieta);
    }

    public Dieta atualizar(Long id, Dieta dietaAtualizada) {
        Dieta atual = localizarPorId(id);
        atual.setDescricao(dietaAtualizada.getDescricao());
        atual.setPaciente(dietaAtualizada.getPaciente());
        return dietaRepository.save(atual);
    }

    public void remover(Long id) {
        Dieta dieta = localizarPorId(id);
        dietaRepository.delete(dieta);
    }

    private Dieta localizarPorId(Long id) {
        return dietaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dieta nao encontrada."
                ));
    }
}
