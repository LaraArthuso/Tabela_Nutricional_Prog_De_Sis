package com.tabelanutricional.nutricional.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tabelanutricional.nutricional.model.Dieta;
import com.tabelanutricional.nutricional.model.Paciente;
import com.tabelanutricional.nutricional.repository.DietaRepository;
import com.tabelanutricional.nutricional.repository.PacienteRepository;

@Service
public class DietaService {

    private final DietaRepository dietaRepository;
    private final PacienteRepository pacienteRepository;

    public DietaService(DietaRepository dietaRepository,
                        PacienteRepository pacienteRepository) {
        this.dietaRepository = dietaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public List<Dieta> listarTodos() {
        return dietaRepository.findAll();
    }

    public Dieta buscarPorId(Long id) {
        return localizarPorId(id);
    }

    public Dieta salvar(Dieta dieta) {
        validarDieta(dieta);

        Paciente paciente = localizarPaciente(dieta.getPaciente().getId());
        dieta.setPaciente(paciente);

        return dietaRepository.save(dieta);
    }

    public Dieta atualizar(Long id, Dieta dietaAtualizada) {
        validarDieta(dietaAtualizada);

        Dieta atual = localizarPorId(id);
        Paciente paciente = localizarPaciente(dietaAtualizada.getPaciente().getId());

        atual.setDescricao(dietaAtualizada.getDescricao());
        atual.setPaciente(paciente);

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

    private Paciente localizarPaciente(Long pacienteId) {
        return pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Paciente nao encontrado."
                ));
    }

    private void validarDieta(Dieta dieta) {
        if (dieta == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O corpo da requisicao e obrigatorio."
            );
        }

        if (estaVazio(dieta.getDescricao())
                || dieta.getPaciente() == null
                || dieta.getPaciente().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Descricao e paciente sao obrigatorios."
            );
        }
    }

    private boolean estaVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}