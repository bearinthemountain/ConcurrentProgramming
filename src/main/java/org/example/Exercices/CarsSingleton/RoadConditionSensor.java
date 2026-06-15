package org.example.Exercices.CarsSingleton;

public class RoadConditionSensor implements Sensor {

    // Bonne pratique : toujours mettre les variables en 'private'
    private String data = "rapide";

    @Override
    public String getData() {
        // Au lieu de "", on retourne la valeur de notre variable
        return this.data;
    }

    // (Optionnel) Si la condition de la route change pendant le trajet,
    // tu auras besoin de cette méthode pour mettre à jour la donnée :
    public void setData(String newData) {
        this.data = newData;
    }
}