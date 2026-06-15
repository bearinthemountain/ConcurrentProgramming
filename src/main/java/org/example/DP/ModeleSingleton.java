package org.example.DP;


/**
 * PATTERN SINGLETON (Version Double-Checked Locking - Thread Safe & Performant)
 * À utiliser quand on te demande une classe qui ne doit être instanciée qu'UNE SEULE fois.
 */
public class ModeleSingleton {

    // 1. VARIABLE STATIQUE ET VOLATILE (Crucial pour le multithreading)
    // 'volatile' garantit que tous les threads voient la même version de la variable [cite: 3141, 3143]
    private static volatile ModeleSingleton instanceUnique;

    // --- METS TES VARIABLES D'ÉTAT ICI ---
    // Exemple : private int volumeRadio = 5;

    // 2. CONSTRUCTEUR PRIVÉ
    // Empêche les autres classes de faire 'new ModeleSingleton()' [cite: 3018]
    private ModeleSingleton() {
        // Initialisation de ton système unique ici
    }

    // 3. LA MÉTHODE D'ACCÈS GLOBALE
    // C'est le seul moyen d'obtenir l'objet. [cite: 3050]
    public static ModeleSingleton getInstance() {
        // Double-checked locking : On vérifie d'abord sans verrouiller (pour la performance) [cite: 3138]
        if (instanceUnique == null) {
            // Si c'est null, on verrouille la classe pour éviter que 2 threads la créent en même temps
            synchronized (ModeleSingleton.class) {
                // On revérifie une fois dans le bloc sécurisé [cite: 3157]
                if (instanceUnique == null) {
                    instanceUnique = new ModeleSingleton();
                }
            }
        }
        return instanceUnique;
    }

    // --- METS TES MÉTHODES MÉTIERS ICI ---
    // Exemple : public void setVolume(int v) { this.volumeRadio = v; }
}