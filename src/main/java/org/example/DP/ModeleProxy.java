package org.example.DP;


/**
 * PATTERN PROXY (Protection Proxy)
 * À utiliser quand on veut limiter ou surveiller l'accès à une méthode.
 */
public class ModeleProxy {

    // ==========================================
    // 1. L'INTERFACE COMMUNE (Subject) [cite: 2811, 2830]
    // ==========================================
    public interface BanqueAccess {
        void retirerArgent(String utilisateur, int montant); // EXAMEN: Adapte selon l'énoncé
    }

    // ==========================================
    // 2. L'OBJET RÉEL (RealSubject - Fait le vrai travail) [cite: 2813]
    // ==========================================
    public static class RealBanqueAccess implements BanqueAccess {
        public void retirerArgent(String utilisateur, int montant) {
            System.out.println("Opération bancaire effectuée : " + montant + " retiré.");
        }
    }

    // ==========================================
    // 3. LE PROXY (Le Videur / Contrôleur d'accès) [cite: 2815, 2951]
    // ==========================================
    public static class ProxyBanqueAccess implements BanqueAccess {
        // Il possède une référence vers l'objet réel [cite: 2948]
        private RealBanqueAccess vraieBanque;

        public void retirerArgent(String utilisateur, int montant) {
            // EXAMEN : C'est ICI qu'est la magie du Proxy. On met les conditions (if). [cite: 2901]
            if (utilisateur.equals("Blacklisté")) {
                System.out.println("ACCÈS REFUSÉ : Utilisateur sur liste noire !");
            } else {
                // Si tout est ok, on instancie l'objet réel (s'il n'existe pas) et on lui passe le relais. [cite: 2902, 2904]
                if (vraieBanque == null) {
                    vraieBanque = new RealBanqueAccess();
                }
                vraieBanque.retirerArgent(utilisateur, montant);
            }
        }
    }

    // --- TEST POUR L'EXAMEN ---
    public static void main(String[] args) {
        BanqueAccess guichet = new ProxyBanqueAccess();
        guichet.retirerArgent("ClientNormal", 100); // Passe
        guichet.retirerArgent("Blacklisté", 100); // Bloqué par le proxy !
    }
}