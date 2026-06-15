package org.example.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * PATTERN COMPOSITE
 * À utiliser pour des structures en arbre (ex: Fichiers et Dossiers, Menus et Sous-menus).
 */
public class ModeleComposite {

    // ==========================================
    // 1. LE COMPONENT (L'interface commune abstraite) [cite: 1693, 1776]
    // ==========================================
    public static abstract class Composant {
        // EXAMEN : On jette des UnsupportedOperationException par défaut !
        // Ainsi, les "Feuilles" n'auront pas à implémenter add() ou remove() inutilement. [cite: 1779]
        public void add(Composant c) { throw new UnsupportedOperationException(); }
        public void remove(Composant c) { throw new UnsupportedOperationException(); }
        public Composant getChild(int i) { throw new UnsupportedOperationException(); }

        public abstract void operation(); // EXAMEN: Remplacer par print(), getPrice(), etc. [cite: 1801]
    }

    // ==========================================
    // 2. LE LEAF (La Feuille : L'élément final qui n'a pas d'enfant) [cite: 1692, 1933]
    // ==========================================
    public static class Feuille extends Composant {
        private String nom;
        public Feuille(String nom) { this.nom = nom; }

        @Override
        public void operation() {
            System.out.println("  - Feuille : " + nom);
        }
    }

    // ==========================================
    // 3. LE COMPOSITE (Le Nœud : Contient des feuilles ou d'autres composites) [cite: 1698, 1853]
    // ==========================================
    public static class NoeudComposite extends Composant {
        private String nom;
        // La liste magique qui permet l'arborescence infinie [cite: 1855]
        private List<Composant> enfants = new ArrayList<>();

        public NoeudComposite(String nom) { this.nom = nom; }

        // Ici, on remplace les exceptions par la vraie logique [cite: 1861]
        @Override
        public void add(Composant c) { enfants.add(c); }
        @Override
        public void remove(Composant c) { enfants.remove(c); }
        @Override
        public Composant getChild(int i) { return enfants.get(i); }

        @Override
        public void operation() {
            System.out.println("Nœud : " + nom);
            // EXAMEN : Le composite parcourt TOUS ses enfants et appelle 'operation()' [cite: 1895, 1899]
            for (Composant enfant : enfants) {
                enfant.operation();
            }
        }
    }

    // --- TEST POUR L'EXAMEN ---
    public static void main(String[] args) {
        Composant dossierPrincipal = new NoeudComposite("Dossier Principal");
        Composant sousDossier = new NoeudComposite("Sous-dossier");
        Composant fichier1 = new Feuille("Fichier_A.txt");
        Composant fichier2 = new Feuille("Fichier_B.txt");

        // Construction de l'arbre
        dossierPrincipal.add(fichier1);
        dossierPrincipal.add(sousDossier);
        sousDossier.add(fichier2);

        // Un seul appel déclenche toute la cascade !
        dossierPrincipal.operation();
    }
}