package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.ComposicaoNutricional;
import com.tabelanutricional.nutricional.repository.ComposicaoNutricionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ComposicaoNutricionalService {

    @Autowired
    private ComposicaoNutricionalRepository composicaoNutricionalRepository;

    public List<ComposicaoNutricional> listarTodos() {
        return composicaoNutricionalRepository.findAll();
    }

    public ComposicaoNutricional buscarPorId(Long id) {
        return localizarPorId(id);
    }

    public ComposicaoNutricional salvar(ComposicaoNutricional composicaoNutricional) {
        return composicaoNutricionalRepository.save(composicaoNutricional);
    }

    public ComposicaoNutricional atualizar(Long id, ComposicaoNutricional cnAtualizada) {
        ComposicaoNutricional atual = localizarPorId(id);
        atual.setQuantidade(cnAtualizada.getQuantidade());
        atual.setRefeicao(cnAtualizada.getRefeicao());
        atual.setAlimento(cnAtualizada.getAlimento());
        return composicaoNutricionalRepository.save(atual);
    }

    public void remover(Long id) {
        ComposicaoNutricional cn = localizarPorId(id);
        composicaoNutricionalRepository.delete(cn);
    }

    private ComposicaoNutricional localizarPorId(Long id) {
        return composicaoNutricionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Composicao nutricional nao encontrada."
                ));
    }
}