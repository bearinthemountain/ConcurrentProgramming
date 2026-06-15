package org.example.DP.proxy;

/**
 * Sujet Réel (Real Subject) : L'objet lourd à charger et instancier.
 * Son chargement est coûteux en temps/mémoire.
 */
public class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk(); // Opération lourde simulant le chargement
    }

    private void loadFromDisk() {
        System.out.println("[RealImage] Chargement lourd du fichier '" + filename + "' depuis le disque...");
        try {
            // Simulation d'une latence d'I/O de 1.5 seconde
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[RealImage] Fichier '" + filename + "' chargé avec succès !");
    }

    @Override
    public void display() {
        System.out.println("[RealImage] Affichage à l'écran de l'image : " + filename);
    }
}
