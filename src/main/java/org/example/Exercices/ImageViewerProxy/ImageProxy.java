package org.example.Exercices.ImageViewerProxy;

public class ImageProxy implements Image {

    // 1. Pour stocker le chemin du fichier ("sample/veryHighResPhoto1.jpeg")
    private String imagePath;

    // 2. La référence vers le VRAI objet (qui sera créé plus tard)
    // (Je l'appelle RealImage, il faudra que tu crées cette classe juste après)
    private RealImage realImage;

    // 3. Le constructeur (Appelé dans le main)
    public ImageProxy(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public void showImage(User user) {

        // 4. On vérifie si l'utilisateur a les droits (Protection Proxy)
        // On simule l'appel au RegistrationService vu dans le main
        if (RegistrationService.isRegistered(user)) {

            // 5. Instanciation paresseuse / Lazy Instantiation (Virtual Proxy)
            // On ne charge la vraie image (très lourde) QUE s'il a le droit de la voir !
            if (realImage == null) {
                realImage = new RealImage(imagePath);
            }

            // On passe le relais au vrai objet pour qu'il s'affiche
            realImage.showImage(user);

        } else {
            System.out.println("Accès refusé pour " + user.getName() + " : Seulement la basse résolution est affichée.");
        }
    }
}