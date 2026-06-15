package org.example.DP.proxy;

/**
 * Proxy Distant (Remote Proxy) : Représente localement un objet s'exécutant sur une autre machine/JVM.
 * Il sert d'intermédiaire et gère la complexité technique liée aux communications réseau :
 * 1. Connexion réseau (Sockets, RMI, HTTP)
 * 2. Sérialisation des arguments (Conversion de l'objet Java en flux binaire ou JSON)
 * 3. Envoi et attente de la réponse (Gestion de la latence, Timeout)
 * 4. Désérialisation de la réponse réseau en objet Java utilisable par le client.
 */
public class ProxyRemoteDataService implements DataService {
    // Référence vers le service réel (ici simulé en local, mais représentant le serveur distant)
    private RemoteDataService remoteService;

    public ProxyRemoteDataService() {
        System.out.println("[Proxy Distant] Initialisation du Stub réseau. Connexion au serveur distant sur le port 8080...");
        this.remoteService = new RemoteDataService();
    }

    @Override
    public String fetchData(String query) throws Exception {
        System.out.println("[Proxy Distant] Appel de la méthode distante. Préparation du paquet réseau...");
        
        // Etape 1 : Sérialisation (Simulation)
        String requestPayload = "{\"request_type\":\"GET\", \"param\":\"" + query + "\"}";
        System.out.println("[Proxy Distant] Sérialisation en JSON : " + requestPayload);

        // Etape 2 : Transmission réseau avec latence
        System.out.println("[Proxy Distant] Transmission des octets via TCP/IP... (Latence réseau simulée)");
        Thread.sleep(800); // 800 ms de latence réseau

        // Etape 3 : Délégation au sujet réel (le serveur)
        String rawResponse = remoteService.fetchData(query);

        // Etape 4 : Réception et désérialisation
        System.out.println("[Proxy Distant] Réception du paquet de réponse...");
        Thread.sleep(400); // Latence de retour
        
        System.out.println("[Proxy Distant] Désérialisation du JSON de réponse : {\"status\":\"SUCCESS\", \"data\":\"" + rawResponse + "\"}");
        return rawResponse;
    }
}
