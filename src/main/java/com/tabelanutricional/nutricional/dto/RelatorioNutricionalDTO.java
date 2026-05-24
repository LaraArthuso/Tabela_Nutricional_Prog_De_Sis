package com.tabelanutricional.nutricional.dto;

import java.util.List;

/**
 * DTO que representa o relatório nutricional de um paciente.
 * Agrega dados locais do banco com dados em tempo real da API Fruityvice.
 */
public class RelatorioNutricionalDTO {

    // --- Dados do paciente (banco local) ---
    private Long pacienteId;
    private String nome;
    private Integer idade;
    private Double peso;
    private Double altura;
    private Double imc; // calculado localmente: peso / (altura * altura)

    // --- Dietas do paciente (banco local) ---
    private List<String> dietas;

    // --- Refeições com alimentos (local + tempo real) ---
    private List<RefeicaoDTO> refeicoes;

    // --- Totais nutricionais somados de todas as refeições ---
    private Double totalCalorias;
    private Double totalProteinas;
    private Double totalCarboidratos;
    private Double totalGorduras;

    // -------------------------------------------------------
    // Classe interna: representa uma refeição no relatório
    // -------------------------------------------------------
    public static class RefeicaoDTO {
        private Long id;
        private String data;
        private String tipo;
        private List<AlimentoDTO> alimentos;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getData() { return data; }
        public void setData(String data) { this.data = data; }

        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }

        public List<AlimentoDTO> getAlimentos() { return alimentos; }
        public void setAlimentos(List<AlimentoDTO> alimentos) { this.alimentos = alimentos; }
    }

    // -------------------------------------------------------
    // Classe interna: representa um alimento no relatório,
    // com indicação se os dados vieram do banco ou da API
    // -------------------------------------------------------
    public static class AlimentoDTO {
        private String nome;
        private Double calorias;
        private Double proteinas;
        private Double carboidratos;
        private Double gorduras;
        private String origem; // "banco local" ou "Fruityvice (tempo real)"

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public Double getCalorias() { return calorias; }
        public void setCalorias(Double calorias) { this.calorias = calorias; }

        public Double getProteinas() { return proteinas; }
        public void setProteinas(Double proteinas) { this.proteinas = proteinas; }

        public Double getCarboidratos() { return carboidratos; }
        public void setCarboidratos(Double carboidratos) { this.carboidratos = carboidratos; }

        public Double getGorduras() { return gorduras; }
        public void setGorduras(Double gorduras) { this.gorduras = gorduras; }

        public String getOrigem() { return origem; }
        public void setOrigem(String origem) { this.origem = origem; }
    }

    // -------------------------------------------------------
    // Getters e Setters do relatório principal
    // -------------------------------------------------------
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }

    public Double getImc() { return imc; }
    public void setImc(Double imc) { this.imc = imc; }

    public List<String> getDietas() { return dietas; }
    public void setDietas(List<String> dietas) { this.dietas = dietas; }

    public List<RefeicaoDTO> getRefeicoes() { return refeicoes; }
    public void setRefeicoes(List<RefeicaoDTO> refeicoes) { this.refeicoes = refeicoes; }

    public Double getTotalCalorias() { return totalCalorias; }
    public void setTotalCalorias(Double totalCalorias) { this.totalCalorias = totalCalorias; }

    public Double getTotalProteinas() { return totalProteinas; }
    public void setTotalProteinas(Double totalProteinas) { this.totalProteinas = totalProteinas; }

    public Double getTotalCarboidratos() { return totalCarboidratos; }
    public void setTotalCarboidratos(Double totalCarboidratos) { this.totalCarboidratos = totalCarboidratos; }

    public Double getTotalGorduras() { return totalGorduras; }
    public void setTotalGorduras(Double totalGorduras) { this.totalGorduras = totalGorduras; }
}
