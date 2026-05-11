package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.ComposicaoNutricional;
import com.tabelanutricional.nutricional.repository.ComposicaoNutricionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComposicaoNutricionalService {

    @Autowired
    private ComposicaoNutricionalRepository composicaoNutricionalRepository;

    public ComposicaoNutricional salvar(ComposicaoNutricional composicaoNutricional) {
        return composicaoNutricionalRepository.save(composicaoNutricional);
    }

    public List<ComposicaoNutricional> listarTodos() {
        return composicaoNutricionalRepository.findAll();
    }
}