package org.example.Exercices.ImageViewerProxy;

public class RealImage implements Image {

    // Le chemin de l'image est stocké ici une fois pour toutes
    private String filename;

    // Le constructeur reçoit le chemin du fichier
    public RealImage(String filename) {
        this.filename = filename;
        System.out.println("Chargement du fichier lourd depuis le disque : " + filename);
    }

    @Override
    public void showImage(User user) {
        // C'est ici que le "travail lourd" est effectué
        System.out.println("Image affichée en HD : " + filename);
    }
}