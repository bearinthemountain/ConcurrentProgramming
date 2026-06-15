package org.example.DP.proxy;

/**
 * Proxy Virtuel (Virtual Proxy) : Contrôle l'instanciation de RealImage.
 * L'objet lourd n'est instancié QUE lorsque la méthode display() est appelée (Lazy Initialization).
 * Si la méthode n'est jamais appelée, l'objet lourd n'occupera jamais de place en mémoire.
 */
public class ProxyImage implements Image {
    private RealImage realImage; // Référence vers le sujet réel
    private String filename;

    public ProxyImage(String filename) {
        this.filename = filename;
        // IMPORTANT : On ne charge PAS l'image ici. L'instanciation lourde est différée.
    }

    @Override
    public void display() {
        // Chargement à la demande (Lazy Loading / Instanciation paresseuse)
        if (realImage == null) {
            System.out.println("[ProxyImage] Premier appel détecté. Instanciation du RealImage...");
            realImage = new RealImage(filename);
        } else {
            System.out.println("[ProxyImage] Image déjà chargée. Réutilisation de l'instance existante.");
        }
        realImage.display();
    }
}
