package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.model.Paciente;
import com.tabelanutricional.nutricional.model.Refeicao;
import com.tabelanutricional.nutricional.repository.PacienteRepository;
import com.tabelanutricional.nutricional.repository.RefeicaoRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RefeicaoService {

    private final RefeicaoRepository refeicaoRepository;
    private final PacienteRepository pacienteRepository;

    public RefeicaoService(RefeicaoRepository refeicaoRepository,
                           PacienteRepository pacienteRepository) {
        this.refeicaoRepository = refeicaoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public List<Refeicao> listarTodos() {
        return refeicaoRepository.findAll();
    }

    public Refeicao buscarPorId(Long id) {
        return localizarPorId(id);
    }

    public Refeicao cadastrar(Refeicao refeicao) {
        validarRefeicao(refeicao);

        Long pacienteId = refeicao.getPaciente().getId();

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Paciente nao encontrado."
                ));

        refeicao.setPaciente(paciente);

        return refeicaoRepository.save(refeicao);
    }

    public Refeicao atualizar(Long id, Refeicao refeicaoAtualizada) {
        validarRefeicao(refeicaoAtualizada);

        Refeicao atual = localizarPorId(id);

        Long pacienteId = refeicaoAtualizada.getPaciente().getId();

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Paciente nao encontrado."
                ));

        atual.setData(refeicaoAtualizada.getData());
        atual.setTipo(refeicaoAtualizada.getTipo());
        atual.setPaciente(paciente);

        return refeicaoRepository.save(atual);
    }

    public void remover(Long id) {
        Refeicao refeicao = localizarPorId(id);
        refeicaoRepository.delete(refeicao);
    }

    private Refeicao localizarPorId(Long id) {
        return refeicaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Refeicao nao encontrada."
                ));
    }

    private void validarRefeicao(Refeicao refeicao) {
        if (refeicao == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O corpo da requisicao e obrigatorio."
            );
        }

        if (estaVazio(refeicao.getData())
                || estaVazio(refeicao.getTipo())
                || refeicao.getPaciente() == null
                || refeicao.getPaciente().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Data, tipo e paciente sao obrigatorios."
            );
        }
    }

    private boolean estaVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}