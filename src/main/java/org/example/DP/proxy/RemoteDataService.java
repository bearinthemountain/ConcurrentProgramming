package org.example.DP.proxy;

/**
 * Sujet Réel (Real Subject) : S'exécute théoriquement sur un serveur distant (autre JVM, conteneur ou machine).
 * Il effectue le véritable traitement métier.
 */
public class RemoteDataService implements DataService {
    @Override
    public String fetchData(String query) {
        System.out.println("[Serveur Réel] Traitement de la requête sur la base de données distante pour : '" + query + "'");
        return "Données Distantes : [Resultat pour '" + query + "']";
    }
}
