package org.example.DP;


/**
 * REGROUPEMENT COMPLET DU PATTERN SINGLETON
 * * L'objectif du Singleton est de garantir qu'une classe n'a qu'une seule instance
 * et de fournir un point d'accès global à cette instance.
 *
 */
public class LabTousLesSingletons {

    // ========================================================================
    // 1. LE CLASSIQUE (Lazy Instantiation basique)
    // ========================================================================
    /**
     * QUAND L'UTILISER :
     * UNIQUEMENT si tu es absolument certain que ton application n'a qu'un SEUL thread.
     * * PIÈGE D'EXAMEN :
     * Si 2 threads entrent dans la méthode en même temps, ils peuvent créer 2 objets.
     * Ne l'utilise jamais dans un environnement multithread !
     */
    public static class SingletonClassique {
        private static SingletonClassique instanceUnique;

        private SingletonClassique() { System.out.println("Création du Classique"); }

        public static SingletonClassique getInstance() {
            if (instanceUnique == null) {
                // Instanciation "Lazy" (Paresseuse) : on attend le dernier moment
                instanceUnique = new SingletonClassique();
            }
            return instanceUnique;
        }
    }


    // ========================================================================
    // 2. LE SYNCHRONISÉ (Thread-Safe mais très lent)
    // ========================================================================
    /**
     * QUAND L'UTILISER :
     * Si tu as plusieurs threads, mais que les performances de l'application
     * ne sont pas critiques (ex: cette méthode est appelée très rarement).
     * * PIÈGE D'EXAMEN :
     * Le mot-clé 'synchronized' force chaque thread à faire la queue. Cela peut
     * diminuer les performances par un facteur de 100 !
     */
    public static class SingletonSynchronise {
        private static SingletonSynchronise instanceUnique;

        private SingletonSynchronise() { System.out.println("Création du Synchronisé"); }

        // Le verrou (synchronized) est mis sur TOUTE la méthode
        public static synchronized SingletonSynchronise getInstance() {
            if (instanceUnique == null) {
                instanceUnique = new SingletonSynchronise();
            }
            return instanceUnique;
        }
    }


    // ========================================================================
    // 3. LA CRÉATION HÂTIVE (Eager Instantiation) -> LE PLUS SIMPLE
    // ========================================================================
    /**
     * QUAND L'UTILISER :
     * Si ton objet n'est pas trop lourd en mémoire, ou si tu sais que ton
     * application en aura de toute façon besoin.
     * -> C'est le choix PAR DÉFAUT à l'examen si on ne te parle pas de performances mémoire.
     * * PIÈGE D'EXAMEN (L'Avantage) :
     * C'est 100% garanti "Thread-Safe" par la machine virtuelle Java elle-même,
     * sans avoir besoin de coder des verrous complexes !
     */
    public static class SingletonEager {
        // L'objet est créé tout de suite, dès le chargement de la classe !
        private static SingletonEager instanceUnique = new SingletonEager();

        private SingletonEager() { System.out.println("Création de l'Eager"); }

        public static SingletonEager getInstance() {
            // On a juste à le retourner, il existe déjà.
            return instanceUnique;
        }
    }


    // ========================================================================
    // 4. LE DOUBLE-CHECKED LOCKING -> LA SOLUTION ULTIME
    // ========================================================================
    /**
     * QUAND L'UTILISER :
     * Si la performance est CRITIQUE, que tu as des MILLIERS de threads,
     * et que l'objet est lourd (donc tu veux le créer en "Lazy").
     * * PIÈGE D'EXAMEN :
     * Si tu oublies le mot-clé 'volatile', ton code est faux ! 'volatile' empêche
     * la mise en cache par les processeurs et garantit que tous les threads
     * voient l'instance correctement.
     */
    public static class SingletonDoubleCheck {
        // MOT-CLÉ OBLIGATOIRE POUR QUE ÇA FONCTIONNE !
        private volatile static SingletonDoubleCheck instanceUnique;

        private SingletonDoubleCheck() { System.out.println("Création du Double-Check"); }

        public static SingletonDoubleCheck getInstance() {
            // 1er Check : On vérifie sans bloquer (pour la rapidité)
            if (instanceUnique == null) {

                // On bloque uniquement la première fois (pour la sécurité)
                synchronized (SingletonDoubleCheck.class) {

                    // 2ème Check : Une fois dans la zone sécurisée, on revérifie
                    if (instanceUnique == null) {
                        instanceUnique = new SingletonDoubleCheck();
                    }
                }
            }
            return instanceUnique;
        }
    }

    // ========================================================================
    // TEST RAPIDE
    // ========================================================================
    public static void main(String[] args) {
        System.out.println("--- Démarrage ---");
        // Note : L'Eager s'est peut-être déjà créé avant même l'appel !

        SingletonClassique.getInstance();
        SingletonSynchronise.getInstance();
        SingletonEager.getInstance();
        SingletonDoubleCheck.getInstance();
    }
}