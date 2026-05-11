package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.Dieta;
import com.tabelanutricional.nutricional.repository.DietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietaService {

    @Autowired
    private DietaRepository dietaRepository;

    public Dieta salvar(Dieta dieta) {
        return dietaRepository.save(dieta);
    }

    public List<Dieta> listarTodos() {
        return dietaRepository.findAll();
    }
}
