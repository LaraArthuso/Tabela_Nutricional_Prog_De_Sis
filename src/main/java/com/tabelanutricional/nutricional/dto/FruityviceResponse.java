//dto para mapear a resposta daapi  


//tentei colocar os atributos em pt mas deu erro ai tinha que colocar em ingles kkkk tive que mudar em alimento
package com.tabelanutricional.nutricional.dto;

public class FruityviceResponse {

    private String name;
    private Nutritions nutritions;

    public String getName() {
        return name;
    }

    public Nutritions getNutritions() {
        return nutritions;
    }

    public static class Nutritions {

        private Double calories;
        private Double protein;
        private Double carbohydrates;
        private Double fat;
        private Double sugar;

        public Double getCalories() {
            return calories;
        }

        public Double getProtein() {
            return protein;
        }

        public Double getCarbohydrates() {
            return carbohydrates;
        }

        public Double getFat() {
            return fat;
        }

        public Double getSugar() {
            return sugar;
        }
    }
}