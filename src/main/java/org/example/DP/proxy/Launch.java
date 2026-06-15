package org.example.DP.proxy;

public class Launch {
    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println("   DEMONSTRATION DES 3 TYPES DE PATRON PROXY");
            System.out.println("==================================================\n");

            ProxyPatternExamRevision.displayRevisionSummary();
            System.out.println("\n--------------------------------------------------");

            // 1. PROTECTION PROXY DEMO
            runProtectionProxyDemo();

            System.out.println("\n--------------------------------------------------");

            // 2. VIRTUAL PROXY DEMO
            runVirtualProxyDemo();

            System.out.println("\n--------------------------------------------------");

            // 3. REMOTE PROXY DEMO
            runRemoteProxyDemo();

            System.out.println("\n==================================================");
            System.out.println("             FIN DE LA DEMONSTRATION");
            System.out.println("==================================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runProtectionProxyDemo() throws Exception {
        System.out.println("[DEMO 1] PROXY DE PROTECTION (Contrôle d'accès)");
        System.out.println("Description : Limite l'accès à certaines ressources en fonction des droits utilisateur.\n");

        User admin = new User("Alice", true);
        User student = new User("Bob", false);

        Navigation nav = new ProxyNavigation();

        System.out.println("--- 1. Tentative d'accès par un étudiant (Bob) ---");
        nav.navigate(student, "https://intranet.hevs.ch"); // Devrait être bloqué

        System.out.println("\n--- 2. Tentative d'accès par un administrateur (Alice) ---");
        nav.navigate(admin, "https://intranet.hevs.ch");   // Devrait être autorisé
    }

    private static void runVirtualProxyDemo() {
        System.out.println("[DEMO 2] PROXY VIRTUEL (Lazy Loading)");
        System.out.println("Description : Retarde le chargement d'un objet lourd jusqu'à son utilisation réelle.\n");

        System.out.println("--- 1. Création du proxy de l'image (aucune instanciation lourde) ---");
        Image image = new ProxyImage("examen_architecture.png");
        System.out.println("Proxy d'image créé avec succès. (Regardez la console : aucun chargement n'a eu lieu !)");

        System.out.println("\n--- 2. Premier appel de display() ---");
        image.display(); // Instancie l'image réelle (latence simulée) et l'affiche

        System.out.println("\n--- 3. Deuxième appel de display() ---");
        image.display(); // Réutilise l'image déjà chargée instantanément
    }

    private static void runRemoteProxyDemo() throws Exception {
        System.out.println("[DEMO 3] PROXY DISTANT (Remote Proxy)");
        System.out.println("Description : Représente localement un objet qui tourne sur une autre machine/JVM.\n");

        System.out.println("--- 1. Création du proxy distant ---");
        DataService service = new ProxyRemoteDataService();

        System.out.println("\n--- 2. Appel de la méthode distante via le proxy ---");
        String result = service.fetchData("SELECT * FROM students WHERE grade >= 5.0");
        
        System.out.println("\n[Client] Résultat reçu par le client : " + result);
    }
}