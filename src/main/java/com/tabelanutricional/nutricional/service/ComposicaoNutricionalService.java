package com.tabelanutricional.nutricional.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tabelanutricional.nutricional.model.Alimento;
import com.tabelanutricional.nutricional.model.ComposicaoNutricional;
import com.tabelanutricional.nutricional.model.Refeicao;
import com.tabelanutricional.nutricional.repository.AlimentoRepository;
import com.tabelanutricional.nutricional.repository.ComposicaoNutricionalRepository;
import com.tabelanutricional.nutricional.repository.RefeicaoRepository;

@Service
public class ComposicaoNutricionalService {

    private final ComposicaoNutricionalRepository composicaoNutricionalRepository;
    private final RefeicaoRepository refeicaoRepository;
    private final AlimentoRepository alimentoRepository;

    public ComposicaoNutricionalService(
            ComposicaoNutricionalRepository composicaoNutricionalRepository,
            RefeicaoRepository refeicaoRepository,
            AlimentoRepository alimentoRepository) {

        this.composicaoNutricionalRepository = composicaoNutricionalRepository;
        this.refeicaoRepository = refeicaoRepository;
        this.alimentoRepository = alimentoRepository;
    }

    public List<ComposicaoNutricional> listarTodos() {
        return composicaoNutricionalRepository.findAll();
    }

    public ComposicaoNutricional buscarPorId(Long id) {
        return localizarPorId(id);
    }

    public ComposicaoNutricional salvar(ComposicaoNutricional composicaoNutricional) {
        validarComposicao(composicaoNutricional);

        Refeicao refeicao = localizarRefeicao(composicaoNutricional.getRefeicao().getId());
        Alimento alimento = localizarAlimento(composicaoNutricional.getAlimento().getId());

        composicaoNutricional.setRefeicao(refeicao);
        composicaoNutricional.setAlimento(alimento);

        return composicaoNutricionalRepository.save(composicaoNutricional);
    }

    public ComposicaoNutricional atualizar(Long id, ComposicaoNutricional composicaoAtualizada) {
        validarComposicao(composicaoAtualizada);

        ComposicaoNutricional atual = localizarPorId(id);

        Refeicao refeicao = localizarRefeicao(composicaoAtualizada.getRefeicao().getId());
        Alimento alimento = localizarAlimento(composicaoAtualizada.getAlimento().getId());

        atual.setQuantidade(composicaoAtualizada.getQuantidade());
        atual.setRefeicao(refeicao);
        atual.setAlimento(alimento);

        return composicaoNutricionalRepository.save(atual);
    }

    public void remover(Long id) {
        ComposicaoNutricional composicaoNutricional = localizarPorId(id);
        composicaoNutricionalRepository.delete(composicaoNutricional);
    }

    private ComposicaoNutricional localizarPorId(Long id) {
        return composicaoNutricionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Composicao nutricional nao encontrada."
                ));
    }

    private Refeicao localizarRefeicao(Long refeicaoId) {
        return refeicaoRepository.findById(refeicaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Refeicao nao encontrada."
                ));
    }

    private Alimento localizarAlimento(Long alimentoId) {
        return alimentoRepository.findById(alimentoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alimento nao encontrado."
                ));
    }

    private void validarComposicao(ComposicaoNutricional composicaoNutricional) {
        if (composicaoNutricional == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O corpo da requisicao e obrigatorio."
            );
        }

        if (composicaoNutricional.getQuantidade() == null
                || composicaoNutricional.getRefeicao() == null
                || composicaoNutricional.getRefeicao().getId() == null
                || composicaoNutricional.getAlimento() == null
                || composicaoNutricional.getAlimento().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantidade, refeicao e alimento sao obrigatorios."
            );
        }
    }
}