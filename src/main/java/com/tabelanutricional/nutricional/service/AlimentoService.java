package com.tabelanutricional.nutricional.service;

import com.tabelanutricional.nutricional.entity.Alimento;
import com.tabelanutricional.nutricional.repository.AlimentoRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tabelanutricional.nutricional.dto.FruityviceResponse;
import org.springframework.web.client.RestClient;

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


//API Fruityvice - Importar alimento por nome da fruta

    public Alimento importarDaFruityvice(String nomeFruta) {

    try {

        RestClient restClient = RestClient.create();


        //essa parte que eu n entendi muito bem, o que é esse .retrieve() e .body() e tal, mas achei um exemplo na internet e tentei adaptar aqui, nao sei se ta certo
        FruityviceResponse resposta = restClient.get()
                .uri("https://www.fruityvice.com/api/fruit/" + nomeFruta)
                .retrieve()
                .body(FruityviceResponse.class);

        if (resposta == null || resposta.getNutritions() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Fruta nao encontrada."
            );
        }

        Alimento alimento = new Alimento();

        alimento.setNome(resposta.getName());
        alimento.setCalorias(resposta.getNutritions().getCalories());
        alimento.setProteinas(resposta.getNutritions().getProtein());
        alimento.setCarboidratos(resposta.getNutritions().getCarbohydrates());
        alimento.setGorduras(resposta.getNutritions().getFat());

        return alimentoRepository.save(alimento);

    } catch (Exception e) {

        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro ao consultar Fruityvice."
        );
    }
}
}