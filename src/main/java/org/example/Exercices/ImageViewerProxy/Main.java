package org.example.Exercices.ImageViewerProxy;

// 1. Il faut absolument une classe pour englober ton main
public class Main {

    public static void main(String[] args) {

        // 2. Définition des utilisateurs (Type déclaré)
        User jean = new User("Jean");
        User paul = new User("Paul");
        User pierre = new User("Pierre");

        RegistrationService.register(paul);

        // 3. Création des images (Type 'Image' déclaré devant la variable)
        Image highResolutionImage1 = new ImageProxy("sample/veryHighResPhoto1.jpeg");
        Image highResolutionImage2 = new ImageProxy("sample/veryHighResPhoto2.jpeg");
        Image highResolutionImage3 = new ImageProxy("sample/veryHighResPhoto3.jpeg");

        // 4. Appel des méthodes
        highResolutionImage1.showImage(jean);
        highResolutionImage2.showImage(paul);
        highResolutionImage3.showImage(pierre);
    }
}