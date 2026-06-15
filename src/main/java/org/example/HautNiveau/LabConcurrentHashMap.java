package org.example.HautNiveau;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LabConcurrentHashMap {

    public static void main(String[] args) {

        // 1. CRÉATION DE LA MAP CONCURRENTE
        // On utilise l'interface ConcurrentMap pour avoir accès aux méthodes atomiques
        ConcurrentMap<String, Double> inventaire = new ConcurrentHashMap<>();

        // 2. UTILISATION DE putIfAbsent()
        // Thread 1 veut ajouter des Pommes
        inventaire.putIfAbsent("Pommes", 2.50);
        System.out.println("Inventaire initial : " + inventaire);

        // Thread 2 essaie aussi d'ajouter des Pommes (peut-être en même temps)
        // Comme "Pommes" existe déjà, cette ligne ne fera RIEN et ne l'écrasera pas !
        inventaire.putIfAbsent("Pommes", 1.00);
        System.out.println("Après tentative de ré-ajout : " + inventaire); // Toujours 2.50 !

        // 3. UTILISATION DE replace(clé, ancienneValeur, nouvelleValeur)
        // C'est super utile pour mettre à jour une valeur sans verrou !
        String article = "Pommes";
        double ancienPrix = 2.50;
        double nouveauPrix = 3.00;

        // "Remplace le prix par 3.00, MAIS SEULEMENT SI le prix actuel est toujours de 2.50"
        // Ça renvoie true si ça a marché, false si un autre thread a modifié le prix entre temps.
        boolean miseAJourReussie = inventaire.replace(article, ancienPrix, nouveauPrix);

        if (miseAJourReussie) {
            System.out.println("Prix mis à jour avec succès : " + inventaire);
        } else {
            System.out.println("Échec : Le prix avait déjà changé !");
        }

        // 4. L'ITERATEUR "WEAKLY CONSISTENT" (Mentionné sur ta diapo)
        // Contrairement à une HashMap normale qui crasherait (ConcurrentModificationException),
        // ici on peut parcourir la Map ALORS QUE d'autres threads la modifient !
        inventaire.put("Bananes", 1.20);
        inventaire.put("Oranges", 3.40);

        System.out.println("\n--- Parcours de l'inventaire ---");
        for (String cle : inventaire.keySet()) {
            System.out.println("- " + cle + " : " + inventaire.get(cle) + " CHF");
            // Si un thread supprime ou ajoute un élément PENDANT cette boucle,
            // le programme ne plantera pas (c'est ça la "Weak Consistency").
        }
    }
}
/*
1. Comprendre la Magie du ConcurrentHashMap
Pour comprendre pourquoi cette classe est géniale, il faut comprendre le problème qu'elle résout.
Regarde la note manuscrite en rouge en bas à droite de ta diapo : elle parle de Collections.synchronizedMap().

Le problème de l'ancienne méthode (synchronizedMap) : Imagine un immense parking. Cette ancienne méthode met une barrière à l'entrée principale. Une seule voiture peut bouger dans tout le parking à la fois. Si un thread lit la case n°1, un autre thread ne peut pas écrire dans la case n°100. C'est un énorme goulot d'étranglement (faible performance).

La solution ConcurrentHashMap (Entry Level Locking) : Au lieu de verrouiller tout le parking, cette classe met un petit verrou sur chaque place de parking (chaque "Entry").

Pour la lecture : C'est porte ouverte ! Les threads peuvent lire en même temps sans se bloquer.

Pour l'écriture : Deux threads peuvent écrire en même temps dans la Map, à condition qu'ils ne touchent pas à la même clé.

2. Les Opérations Atomiques (Le cœur de la diapo)
Ta diapo liste 4 méthodes magiques. On les appelle "atomiques" car elles font une vérification ET une action en une seule étape indivisible. Cela t'évite d'avoir à écrire des blocs synchronized !

putIfAbsent(clé, valeur) : "Ajoute cette valeur uniquement si la clé n'existe pas encore."

remove(clé, valeur) : "Supprime cette clé uniquement si elle contient actuellement cette valeur précise."

replace(clé, valeur) : "Remplace la valeur uniquement si la clé existe déjà."

replace(clé, ancienneValeur, nouvelleValeur) : "Remplace par la nouvelle valeur uniquement si la valeur actuelle est exactement celle que j'attends." (Très utile pour des compteurs !).

3. Le Code Modèle : Le Gestionnaire d'Inventaire
Voici un code parfait pour l'examen. Il simule un système où plusieurs threads (des caissiers) tentent d'ajouter des produits ou de mettre à jour des prix en même temps.

Crée une classe nommée LabConcurrentHashMap :

 */