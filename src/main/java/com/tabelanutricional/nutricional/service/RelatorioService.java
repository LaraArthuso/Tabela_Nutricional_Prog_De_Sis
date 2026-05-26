package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.dto.FruityviceResponse;
import com.tabelanutricional.nutricional.dto.RelatorioNutricionalDTO;
import com.tabelanutricional.nutricional.dto.RelatorioNutricionalDTO.AlimentoDTO;
import com.tabelanutricional.nutricional.dto.RelatorioNutricionalDTO.RefeicaoDTO;
import com.tabelanutricional.nutricional.model.Alimento;
import com.tabelanutricional.nutricional.model.Dieta;
import com.tabelanutricional.nutricional.model.Paciente;
import com.tabelanutricional.nutricional.model.Refeicao;
import com.tabelanutricional.nutricional.repository.DietaRepository;
import com.tabelanutricional.nutricional.repository.PacienteRepository;
import com.tabelanutricional.nutricional.repository.RefeicaoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    private final PacienteRepository pacienteRepository;
    private final DietaRepository dietaRepository;
    private final RefeicaoRepository refeicaoRepository;

    public RelatorioService(PacienteRepository pacienteRepository,
                            DietaRepository dietaRepository,
                            RefeicaoRepository refeicaoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.dietaRepository = dietaRepository;
        this.refeicaoRepository = refeicaoRepository;
    }

    /**
     * Gera o relatório nutricional completo de um paciente,
     * agregando dados locais do banco com dados em tempo real da API Fruityvice.
     */
    public RelatorioNutricionalDTO gerarRelatorio(Long pacienteId) {

        // --- 1. Buscar o paciente no banco local ---
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Paciente nao encontrado."
                ));

        // --- 2. Buscar as dietas do paciente no banco local ---
        List<Dieta> dietas = dietaRepository.findByPacienteId(pacienteId);
        List<String> descricoesDietas = dietas.stream()
                .map(Dieta::getDescricao)
                .toList();

        // --- 3. Buscar as refeições do paciente no banco local ---
        List<Refeicao> refeicoes = refeicaoRepository.findByPacienteId(pacienteId);

        // --- 4. Montar as refeições do relatório, enriquecendo os alimentos ---
        double totalCalorias = 0.0;
        double totalProteinas = 0.0;
        double totalCarboidratos = 0.0;
        double totalGorduras = 0.0;

        List<RefeicaoDTO> refeicoesDTO = new ArrayList<>();

        for (Refeicao refeicao : refeicoes) {

            List<AlimentoDTO> alimentosDTO = new ArrayList<>();

            for (Alimento alimento : refeicao.getAlimentos()) {

                // Tenta buscar dados atualizados da Fruityvice pelo nome do alimento
                AlimentoDTO alimentoDTO = buscarDadosAtualizados(alimento);

                alimentosDTO.add(alimentoDTO);

                // Acumula nos totais (usa 0.0 se o valor for nulo)
                totalCalorias    += alimentoDTO.getCalorias()    != null ? alimentoDTO.getCalorias()    : 0.0;
                totalProteinas   += alimentoDTO.getProteinas()   != null ? alimentoDTO.getProteinas()   : 0.0;
                totalCarboidratos+= alimentoDTO.getCarboidratos()!= null ? alimentoDTO.getCarboidratos(): 0.0;
                totalGorduras    += alimentoDTO.getGorduras()    != null ? alimentoDTO.getGorduras()    : 0.0;
            }

            RefeicaoDTO refeicaoDTO = new RefeicaoDTO();
            refeicaoDTO.setId(refeicao.getId());
            refeicaoDTO.setData(refeicao.getData());
            refeicaoDTO.setTipo(refeicao.getTipo());
            refeicaoDTO.setAlimentos(alimentosDTO);
            refeicoesDTO.add(refeicaoDTO);
        }

        // --- 5. Calcular o IMC localmente ---
        double imc = paciente.getPeso() / (paciente.getAltura() * paciente.getAltura());

        // --- 6. Montar e retornar o relatório final ---
        RelatorioNutricionalDTO relatorio = new RelatorioNutricionalDTO();
        relatorio.setPacienteId(paciente.getId());
        relatorio.setNome(paciente.getNome());
        relatorio.setIdade(paciente.getIdade());
        relatorio.setPeso(paciente.getPeso());
        relatorio.setAltura(paciente.getAltura());
        relatorio.setImc(Math.round(imc * 100.0) / 100.0); // arredonda para 2 casas
        relatorio.setDietas(descricoesDietas);
        relatorio.setRefeicoes(refeicoesDTO);
        relatorio.setTotalCalorias(totalCalorias);
        relatorio.setTotalProteinas(totalProteinas);
        relatorio.setTotalCarboidratos(totalCarboidratos);
        relatorio.setTotalGorduras(totalGorduras);

        return relatorio;
    }

    /**
     * Tenta buscar dados atualizados da Fruityvice pelo nome do alimento.
     * Se não encontrar (alimento não é uma fruta ou API indisponível),
     * usa os dados salvos no banco local como fallback.
     */
    private AlimentoDTO buscarDadosAtualizados(Alimento alimento) {

        AlimentoDTO dto = new AlimentoDTO();
        dto.setNome(alimento.getNome());

        try {
            RestClient restClient = RestClient.create();

            FruityviceResponse resposta = restClient.get()
                    .uri("https://www.fruityvice.com/api/fruit/" + alimento.getNome().toLowerCase())
                    .retrieve()
                    .body(FruityviceResponse.class);

            if (resposta != null && resposta.getNutritions() != null) {
                // Dados vieram da API em tempo real
                dto.setCalorias(resposta.getNutritions().getCalories());
                dto.setProteinas(resposta.getNutritions().getProtein());
                dto.setCarboidratos(resposta.getNutritions().getCarbohydrates());
                dto.setGorduras(resposta.getNutritions().getFat());
                dto.setOrigem("Fruityvice (tempo real)");
                return dto;
            }

        } catch (Exception e) {
            // Fruta não encontrada na API ou erro de rede: usa dados locais
        }

        // Fallback: dados do banco local
        dto.setCalorias(alimento.getCalorias());
        dto.setProteinas(alimento.getProteinas());
        dto.setCarboidratos(alimento.getCarboidratos());
        dto.setGorduras(alimento.getGorduras());
        dto.setOrigem("banco local");

        return dto;
    }
}
