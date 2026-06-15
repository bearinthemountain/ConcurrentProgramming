package org.example.Exercices.ImageViewerProxy;

import java.util.ArrayList;
import java.util.List;

public class RegistrationService {

    // 1. La "base de données" (une liste statique pour garder en mémoire les utilisateurs)
    private static List<User> registeredUsers = new ArrayList<>();

    // 2. La méthode pour enregistrer un utilisateur (vue dans le main de l'énoncé)
    public static void register(User user) {
        registeredUsers.add(user);
        System.out.println("Service : " + user.getName() + " est maintenant enregistré.");
    }

    // 3. La méthode que tu as préparée, pour vérifier les droits (utilisée par le Proxy)
    public static boolean isRegistered(User user) {
        // Retourne 'true' si l'utilisateur est dans la liste, 'false' sinon
        return registeredUsers.contains(user);
    }
}