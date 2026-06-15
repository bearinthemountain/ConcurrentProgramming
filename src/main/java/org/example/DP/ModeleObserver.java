package org.example.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * PATTERN OBSERVER
 * À utiliser pour un système de notification (Publisher / Subscriber). [cite: 2027]
 */
public class ModeleObserver {

    // ==========================================
    // 1. LES INTERFACES [cite: 2082, 2087]
    // ==========================================
    public interface Subject {
        void registerObserver(Observer o);
        void removeObserver(Observer o);
        void notifyObservers();
    }

    public interface Observer {
        void update(String data); // EXAMEN: Adapte les paramètres selon ce que le Subject envoie ! [cite: 2174]
    }

    // ==========================================
    // 2. LE SUJET CONCRET (Celui qui émet les infos) [cite: 2089, 2190]
    // ==========================================
    public static class MonSujet implements Subject {
        // La liste magique de tous les abonnés [cite: 2191]
        private List<Observer> observers = new ArrayList<>();
        private String maDonneeImportante;

        public void registerObserver(Observer o) { observers.add(o); }
        public void removeObserver(Observer o) { observers.remove(o); }

        // EXAMEN : Ne pas oublier la boucle for ! [cite: 2203]
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update(maDonneeImportante);
            }
        }

        // Quand l'état change, on notifie tout le monde ! [cite: 2226]
        public void setDonnee(String data) {
            this.maDonneeImportante = data;
            notifyObservers();
        }
    }

    // ==========================================
    // 3. L'OBSERVATEUR CONCRET (Celui qui écoute) [cite: 2094, 2097]
    // ==========================================
    public static class MonEcran implements Observer {
        private String nom;

        public MonEcran(String nom) { this.nom = nom; }

        @Override
        public void update(String data) {
            System.out.println(nom + " a reçu la mise à jour : " + data);
        }
    }

    // --- TEST POUR L'EXAMEN ---
    public static void main(String[] args) {
        MonSujet sujet = new MonSujet();
        MonEcran ecran1 = new MonEcran("Écran 1");
        MonEcran ecran2 = new MonEcran("Écran 2");

        sujet.registerObserver(ecran1);
        sujet.registerObserver(ecran2);

        sujet.setDonnee("Alerte Tempête !"); // Les deux écrans vont réagir !
    }
}