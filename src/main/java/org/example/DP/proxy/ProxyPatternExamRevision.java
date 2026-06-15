package org.example.DP.proxy;

/**
 * =================================================================================
 *                 FICHE DE RÉVISION EXAMEN : PATRON PROXY (PROXY PATTERN)
 * =================================================================================
 *
 * 1. INTENTION & DÉFINITION :
 * ---------------------------
 * Le Proxy est un patron de structure qui fournit un substitut ou un intermédiaire
 * (un "surveillant") pour contrôler l'accès à un autre objet (le Sujet Réel).
 *
 * RÈGLE D'OR : Le Proxy et le Sujet Réel doivent implémenter la MÊME INTERFACE.
 * Ainsi, le client ne sait pas (et n'a pas besoin de savoir) s'il discute avec le
 * proxy ou le sujet réel.
 *
 *
 * 2. LES 3 TYPES DE PROXIES EXIGÉS À L'EXAMEN :
 * --------------------------------------------
 *
 *   A) PROXY DE PROTECTION (Protection Proxy) :
 *      - Rôle : Contrôle les droits d'accès (sécurité).
 *      - Comment : Il vérifie les autorisations de l'appelant (ex: rôle utilisateur,
 *        droits admin, restrictions IP) avant de déléguer l'appel au sujet réel.
 *      - Code Ref : ProxyNavigation.java (vérifie si l'utilisateur est admin).
 *
 *   B) PROXY VIRTUEL (Virtual Proxy) :
 *      - Rôle : Optimise les performances et l'utilisation de la mémoire.
 *      - Comment : Il retarde l'instanciation coûteuse (chargement d'image lourde,
 *        connexion DB lente) jusqu'à ce que l'objet réel soit vraiment nécessaire.
 *        C'est le principe de l'instanciation paresseuse (Lazy Loading).
 *      - Code Ref : ProxyImage.java (instancie RealImage uniquement au 1er display()).
 *
 *   C) PROXY DISTANT (Remote Proxy) :
 *      - Rôle : Gère la complexité de la communication réseau.
 *      - Comment : Il représente localement un objet qui s'exécute dans un autre
 *        espace mémoire (JVM différente, serveur distant, microservice). Le proxy
 *        (souvent appelé "Stub") masque les détails de réseau (connexion socket,
 *        sérialisation/désérialisation des requêtes JSON/octets, timeouts).
 *      - Code Ref : ProxyRemoteDataService.java (simule la latence et la sérialisation).
 *
 *
 * 3. COMPARAISON ENTRE PATRONS SIMILAIRES (Question d'examen fréquente !) :
 * --------------------------------------------------------------------------
 * Bien que la structure UML soit similaire (relation "has-a" + implémentation d'une
 * interface), l'intention diffère fondamentalement :
 *
 *   - PROXY     : Contrôle l'accès à un objet existant (sans modifier l'interface
 *                 ni ajouter de logique métier additionnelle).
 *   - DECORATOR : Ajoute dynamiquement des fonctionnalités/comportements métiers
 *                 additionnels à un objet sans modifier son interface (ex: rajouter
 *                 du chocolat à un café).
 *   - ADAPTER   : Convertit l'interface d'une classe existante en une autre interface
 *                 différente attendue par le client pour les rendre compatibles.
 *
 *
 * 4. STRUCTURE DE CODE TYPE (À reproduire à l'examen) :
 * ------------------------------------------------------
 *
 *   // Étape 1 : Définir l'interface commune
 *   interface Service {
 *       void execute();
 *   }
 *
 *   // Étape 2 : Créer le sujet réel (le vrai traitement)
 *   class RealService implements Service {
 *       public void execute() {
 *           System.out.println("Traitement réel...");
 *       }
 *   }
 *
 *   // Étape 3 : Créer le proxy
 *   class ProxyService implements Service {
 *       private RealService realService; // Référence vers le sujet réel
 *
 *       public void execute() {
 *           // Exemple 1 : Proxy de Protection (Contrôle d'accès)
 *           // if (!user.hasAccess()) { throw new SecurityException("Accès refusé !"); }
 *
 *           // Exemple 2 : Proxy Virtuel (Lazy loading)
 *           if (realService == null) {
 *               realService = new RealService();
 *           }
 *
 *           // Exemple 3 : Remote Proxy (Pré/Post-processing)
 *           // (Ici on fait la sérialisation et l'appel réseau avant d'appeler l'objet)
 *
 *           realService.execute(); // Délégation
 *       }
 *   }
 *
 * =================================================================================
 */
public class ProxyPatternExamRevision {
    public static void displayRevisionSummary() {
        System.out.println("Fiche de révision du patron Proxy chargée avec succès !");
        System.out.println("Consultez le code source de cette classe pour réviser :");
        System.out.println("- Proxy de Protection");
        System.out.println("- Proxy Virtuel (Lazy Loading)");
        System.out.println("- Proxy Distant (Remote Proxy)");
        System.out.println("- Comparaisons avec Decorator et Adapter");
    }
}
