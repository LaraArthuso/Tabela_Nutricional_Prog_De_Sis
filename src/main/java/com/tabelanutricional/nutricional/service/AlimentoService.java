package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.Alimento;
import com.tabelanutricional.nutricional.repository.AlimentoRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;

    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    public List<Alimento> listarTodos() {
        return alimentoRepository.findAll();
    }

    public Alimento buscarPorId(Long id) {
        return localizarPorId(id);
    }

    public Alimento cadastrar(Alimento alimento) {
        validarAlimento(alimento);
        return alimentoRepository.save(alimento);
    }

    public Alimento atualizar(Long id, Alimento alimentoAtualizado) {
        validarAlimento(alimentoAtualizado);

        Alimento atual = localizarPorId(id);

        atual.setNome(alimentoAtualizado.getNome());
        atual.setCalorias(alimentoAtualizado.getCalorias());
        atual.setProteinas(alimentoAtualizado.getProteinas());
        atual.setCarboidratos(alimentoAtualizado.getCarboidratos());
        atual.setGorduras(alimentoAtualizado.getGorduras());

        return alimentoRepository.save(atual);
    }

    public void remover(Long id) {
        Alimento alimento = localizarPorId(id);
        alimentoRepository.delete(alimento);
    }

    private Alimento localizarPorId(Long id) {
        return alimentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alimento nao encontrado."
                ));
    }

    private void validarAlimento(Alimento alimento) {
        if (alimento == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O corpo da requisicao e obrigatorio."
            );
        }

        if (estaVazio(alimento.getNome())
                || alimento.getCalorias() == null
                || alimento.getProteinas() == null
                || alimento.getCarboidratos() == null
                || alimento.getGorduras() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nome, calorias, proteinas, carboidratos e gorduras sao obrigatorios."
            );
        }
    }

    private boolean estaVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}