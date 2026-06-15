package org.example.BasNiveau;

public final class ImmutableRGB {
    /*
    C'est un excellent réflexe de t'arrêter sur cette notion !
    Les objets immuables (Immutable objects) sont l'arme absolue en programmation concurrente.
À l'examen, si on te demande comment éviter les interférences de threads (Thread Interference)
sans utiliser de verrous compliqués comme synchronized ou ReentrantLock, la réponse magique est :
rendre l'objet immuable.
Puisque l'état de l'objet ne peut pas changer après sa création,
1000 threads peuvent le lire en même temps sans aucun risque de conflit.
Voici tout ce que tu dois savoir, structuré pour tes révisions, avec le code complet et commenté.
Les 4 Règles d'Or de l'Immutabilité (À retenir par cœur)
Pour transformer une classe normale en classe immuable (comme sur ta diapositive),
tu dois appliquer ces 4 règles strictes :
La classe doit être final : Personne ne doit pouvoir créer une sous-classe (héritage)
qui viendrait pirater et modifier les comportements.
Tous les attributs doivent être private final : Ils sont assignés une seule fois dans le constructeur,
et c'est terminé.
Aucun "Setter" : Ne crée aucune méthode qui commence par set...() ou qui modifie les variables internes.
Protéger les objets mutables (La copie défensive) : Si ta classe contient une liste ou une date (qui sont modifiables par nature), tu ne dois jamais renvoyer la référence originale, mais faire une copie avant de la renvoyer. (Dans l'exemple RGB, on n'utilise que des int et des String
qui sont déjà immuables, donc cette règle est validée automatiquement).
     */
    // RÈGLE 1 : La classe est 'final', impossible de faire un "extends ImmutableRGB"

        // RÈGLE 2 : Tous les attributs sont 'private' et 'final'
        // Ils ne recevront une valeur qu'une seule fois.
        private final int red;
        private final int green;
        private final int blue;
        private final String name;

        // Le constructeur est le SEUL moment où on donne une valeur aux attributs.
        public ImmutableRGB(int red, int green, int blue, String name) {
            check(red, green, blue);
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }

        // Méthode de vérification interne (ne change rien à l'immutabilité)
        private void check(int red, int green, int blue) {
            if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
                throw new IllegalArgumentException("Les couleurs doivent être entre 0 et 255");
            }
        }

        // RÈGLE 3 : Des Getters, mais AUCUN Setter !
        public int getRGB() {
            return ((red << 16) | (green << 8) | blue);
        }

        public String getName() {
            return name;
        }

        // ==========================================================
        // L'ASTUCE CRUCIALE : COMMENT "MODIFIER" SANS MODIFIER ?
        // ==========================================================
        // Dans l'ancienne version, invert() modifiait this.red, this.green, etc.
        // Dans une version immuable, l'action de modifier renvoie un NOUVEL objet !
        public ImmutableRGB invert() {
            // On crée et on retourne une TOUTE NOUVELLE instance avec les nouvelles valeurs
            return new ImmutableRGB(
                    255 - red,
                    255 - green,
                    255 - blue,
                    "Inverse de " + name
            );
        }
    }

    // --- Exemple d'utilisation dans le Main ---
    class TestImmutability {
        public static void main(String[] args) {
            // 1. Création de la couleur (Elle est scellée pour toujours)
            ImmutableRGB maCouleur = new ImmutableRGB(255, 0, 0, "Rouge Pur");

            // 2. Si je veux l'inverser, je ne modifie pas 'maCouleur',
            // je récupère un nouvel objet que je stocke dans une nouvelle variable.
            ImmutableRGB couleurInverse = maCouleur.invert();

            System.out.println(maCouleur.getName());      // Affiche toujours "Rouge Pur"
            System.out.println(couleurInverse.getName()); // Affiche "Inverse de Rouge Pur"
        }
    }

