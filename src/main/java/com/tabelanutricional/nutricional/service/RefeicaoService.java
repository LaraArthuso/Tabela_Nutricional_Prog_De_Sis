package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.Refeicao;
import com.tabelanutricional.nutricional.repository.RefeicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefeicaoService {

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    public Refeicao salvar(Refeicao refeicao) {
        return refeicaoRepository.save(refeicao);
    }

    public List<Refeicao> listarTodos() {
        return refeicaoRepository.findAll();
    }
}