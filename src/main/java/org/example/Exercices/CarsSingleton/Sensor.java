package org.example.Exercices.CarsSingleton;

public interface Sensor {

    // 1. Ceci est une CONSTANTE (public static final par défaut)
    // Par convention, on l'écrit en MAJUSCULES.
    String INFO = "Capteur standard";

    // 2. Ceci est la SIGNATURE de la méthode (public abstract par défaut)
    // Elle dit : "Toute classe qui implémente Sensor DOIT avoir une méthode getData() qui retourne une String"
    String getData();

}