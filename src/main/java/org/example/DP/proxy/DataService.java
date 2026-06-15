package org.example.DP.proxy;

/**
 * Interface commune pour le service de données.
 * Définit le contrat utilisé par le client et implémenté par le Proxy et le Service Réel.
 */
public interface DataService {
    String fetchData(String query) throws Exception;
}
