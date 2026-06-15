package org.example.DP.proxy;

import java.util.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class ProxyNavigation implements Navigation {
    private RealNavigation realNavigation; // Référence à l'objet réel
    private List<String> restrictedURLs = Arrays.asList("https://intranet.hevs.ch");

    @Override
    public void navigate(User user, String url) throws IOException, URISyntaxException {
        // Protection : Vérifie si l'URL est restreinte
        boolean isRestricted = restrictedURLs.contains(url);

        if (!isRestricted || user.isAdmin()) {
            // Lazy Instantiation (Virtual Proxy) : On ne crée l'objet réel QUE si besoin
            if (realNavigation == null) {
                realNavigation = new RealNavigation();
            }
            realNavigation.navigate(user, url);
        } else {
            System.out.println("[ProxyNavigation] ACCÈS REFUSÉ à : " + url + " pour l'utilisateur : " + user.getName() + " (Permissions insuffisantes)");
        }
    }
}
