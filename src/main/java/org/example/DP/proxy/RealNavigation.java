package org.example.DP.proxy;


import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

public class RealNavigation implements Navigation {
    @Override
    public void navigate(User user, String url) throws IOException, URISyntaxException {
        // C'est ici que l'action réelle est effectuée
        System.out.println("[RealNavigation] Accès réel autorisé vers : " + url);
        
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                System.out.println("[RealNavigation] Navigateur non disponible dans cet environnement.");
            }
        } else {
            System.out.println("[RealNavigation] (Simulation) Navigation vers : " + url);
        }
    }
}