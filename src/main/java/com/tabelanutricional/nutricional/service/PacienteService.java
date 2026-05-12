package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.Paciente;
import com.tabelanutricional.nutricional.repository.PacienteRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return localizarPorId(id);
    }

    public Paciente cadastrar(Paciente paciente) {
        validarPaciente(paciente);
        return pacienteRepository.save(paciente);
    }

    public Paciente atualizar(Long id, Paciente pacienteAtualizado) {

        validarPaciente(pacienteAtualizado);

        Paciente atual = localizarPorId(id);

        atual.setNome(pacienteAtualizado.getNome());
        atual.setIdade(pacienteAtualizado.getIdade());
        atual.setPeso(pacienteAtualizado.getPeso());
        atual.setAltura(pacienteAtualizado.getAltura());

        return pacienteRepository.save(atual);
    }

    public void remover(Long id) {
        Paciente paciente = localizarPorId(id);
        pacienteRepository.delete(paciente);
    }

    private Paciente localizarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Paciente nao encontrado."
                ));
    }

    private void validarPaciente(Paciente paciente) {

        if (paciente == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O corpo da requisicao e obrigatorio."
            );
        }

        if (estaVazio(paciente.getNome())
                || paciente.getIdade() == null
                || paciente.getPeso() == null
                || paciente.getAltura() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nome, idade, peso e altura sao obrigatorios."
            );
        }
    }

    private boolean estaVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}