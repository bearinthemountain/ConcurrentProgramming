package org.example.DP;

/**
 * PATTERN STRATEGY
 * À utiliser quand un objet (le Context) doit changer de comportement dynamiquement.
 */
public class ModeleStrategy {

    // ==========================================
    // 1. L'INTERFACE STRATEGY (La règle commune) [cite: 2720, 2721]
    // ==========================================
    public interface ComportementStrategy {
        void executerComportement(); // EXAMEN: Change la signature selon ton besoin (ex: double calculerTaxe(double montant))
    }

    // ==========================================
    // 2. LES STRATÉGIES CONCRÈTES (Les algorithmes) [cite: 2722, 2723, 2736]
    // ==========================================
    public static class ComportementA implements ComportementStrategy {
        public void executerComportement() {
            System.out.println("J'exécute l'algorithme A !");
        }
    }

    public static class ComportementB implements ComportementStrategy {
        public void executerComportement() {
            System.out.println("J'exécute l'algorithme B !");
        }
    }

    // ==========================================
    // 3. LE CONTEXTE (L'objet qui utilise la stratégie) [cite: 2718, 2739, 2740]
    // ==========================================
    public static class MonObjetContext {
        // Il possède une référence vers l'interface, pas vers une classe concrète ! [cite: 2452]
        private ComportementStrategy strategie;

        public MonObjetContext(ComportementStrategy strategieInitiale) {
            this.strategie = strategieInitiale;
        }

        // PERMET DE CHANGER LE COMPORTEMENT EN PLEIN VOL ! [cite: 2547]
        public void setStrategie(ComportementStrategy nouvelleStrategie) {
            this.strategie = nouvelleStrategie;
        }

        public void faireLeTravail() {
            // Il délègue le travail à la stratégie [cite: 2546]
            strategie.executerComportement();
        }
    }

    // --- TEST POUR L'EXAMEN ---
    public static void main(String[] args) {
        MonObjetContext objet = new MonObjetContext(new ComportementA());
        objet.faireLeTravail(); // Affiche A

        objet.setStrategie(new ComportementB()); // On change à la volée !
        objet.faireLeTravail(); // Affiche B
    }
}