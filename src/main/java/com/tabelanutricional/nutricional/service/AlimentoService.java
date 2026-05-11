package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.Alimento;
import com.tabelanutricional.nutricional.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    public Alimento salvar(Alimento alimento) {
        return alimentoRepository.save(alimento);
    }

    public List<Alimento> listarTodos() {
        return alimentoRepository.findAll();
    }
}