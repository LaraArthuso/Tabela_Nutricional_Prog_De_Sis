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

    
    public RelatorioNutricionalDTO gerarRelatorio(Long pacienteId) {

        //buscar o paciente no banco local
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Paciente nao encontrado."
                ));

        //as dietas do paciente no banco local
        List<Dieta> dietas = dietaRepository.findByPacienteId(pacienteId);
        List<String> descricoesDietas = dietas.stream() // stream serve para processar 
                .map(Dieta::getDescricao)
                .toList();

        // refeições do paciente no banco local 
        List<Refeicao> refeicoes = refeicaoRepository.findByPacienteId(pacienteId);

        //as refeições do relatório e os totais nutricionais inicializados em 0
        double totalCalorias = 0.0;
        double totalProteinas = 0.0;
        double totalCarboidratos = 0.0;
        double totalGorduras = 0.0;

        List<RefeicaoDTO> refeicoesDTO = new ArrayList<>();

        for (Refeicao refeicao : refeicoes) {

            List<AlimentoDTO> alimentosDTO = new ArrayList<>();

            for (Alimento alimento : refeicao.getAlimentos()) {

                // aqui mistura o local e api
                // busca dados atualizados da Fruityvice pelo nome do alimento 
                AlimentoDTO alimentoDTO = buscarDadosAtualizados(alimento);

                alimentosDTO.add(alimentoDTO);

                // acumula nos totais usa 0.0 se o valor for nulo
                totalCalorias    += alimentoDTO.getCalorias()    != null ? alimentoDTO.getCalorias()    : 0.0;
                totalProteinas   += alimentoDTO.getProteinas()   != null ? alimentoDTO.getProteinas()   : 0.0;
                totalCarboidratos+= alimentoDTO.getCarboidratos()!= null ? alimentoDTO.getCarboidratos(): 0.0;
                totalGorduras    += alimentoDTO.getGorduras()    != null ? alimentoDTO.getGorduras()    : 0.0;
            }
            //aqui formata a refeição do relatório com os dados atualizados dos alimentos
            RefeicaoDTO refeicaoDTO = new RefeicaoDTO();
            refeicaoDTO.setId(refeicao.getId());
            refeicaoDTO.setData(refeicao.getData());
            refeicaoDTO.setTipo(refeicao.getTipo());
            refeicaoDTO.setAlimentos(alimentosDTO);
            refeicoesDTO.add(refeicaoDTO);
        }

        // calcular o imc localmente usando os dados do paciente do banco 
        double imc = paciente.getPeso() / (paciente.getAltura() * paciente.getAltura());

        //monta e retornar o relatório final 
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

    
     //tenta buscar dados atualizados da Fruityvice pelo nome do alimento
     //se não encontrar usa os dados salvos no banco local como fallback
     
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
            //não encontrada na API ou erro de rede usa dados locais
        }

        // fallback: dados do banco local
        dto.setCalorias(alimento.getCalorias());
        dto.setProteinas(alimento.getProteinas());
        dto.setCarboidratos(alimento.getCarboidratos());
        dto.setGorduras(alimento.getGorduras());
        dto.setOrigem("banco local");

        return dto;
    }
}
