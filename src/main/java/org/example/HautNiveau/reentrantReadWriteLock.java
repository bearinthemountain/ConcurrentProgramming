package org.example.HautNiveau;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock; // L'outil officiel de Java

/*
Le readLock ne sert pas à bloquer les autres lecteurs (la porte leur est grande ouverte). Le readLock sert à bloquer les écrivains !

Quand un thread lecteur prend le readLock, il dit au système :

"Je suis en train de lire. D'autres lecteurs peuvent venir lire avec moi, mais interdiction absolue de laisser entrer un écrivain tant que je n'ai pas fini !"


 */

// Ta classe avec un "r" minuscule pour éviter le conflit de nom !
public class reentrantReadWriteLock {

    // 1. LA RESSOURCE PARTAGÉE (Le Fil d'actualité)
    static class NewsFeed {
        private final List<String> articles = new ArrayList<>();

        // LE VERROU : Il sépare la Lecture et l'Écriture
        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

        // --- MÉTHODE D'ÉCRITURE (Le verrou exclusif) ---
        public void publierNews(String news) {
            // writeLock() : Bloque TOUT LE MONDE (lecteurs et autres écrivains)
            rwLock.writeLock().lock();
            try {
                System.out.println("✍️ Le journaliste écrit : " + news);
                articles.add(news);
                Thread.sleep(500); // Temps de rédaction
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                rwLock.writeLock().unlock(); // À NE JAMAIS OUBLIER
            }
        }

        // --- MÉTHODE DE LECTURE (Le verrou partagé) ---
        public void lireNews(String nomLecteur) {
            // readLock() : Laisse passer plusieurs lecteurs en même temps !
            rwLock.readLock().lock();
            try {
                int nbArticles = articles.size();
                System.out.println("👁️ " + nomLecteur + " lit le fil. Il y a " + nbArticles + " article(s).");
                Thread.sleep(100); // Temps de lecture
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                rwLock.readLock().unlock(); // À NE JAMAIS OUBLIER
            }
        }
    }

    // 2. LE PROGRAMME PRINCIPAL (Test pour l'examen)
    public static void main(String[] args) {
        NewsFeed feed = new NewsFeed();

        // Le Thread Producteur (1 seul journaliste)
        Thread journaliste = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                feed.publierNews("Flash Info Numéro " + i);
                try { Thread.sleep(800); } catch (InterruptedException e) {}
            }
        });
        journaliste.start();

        // Les Threads Consommateurs (20 lecteurs simultanés)
        for (int i = 1; i <= 20; i++) {
            final String nom = "Lecteur-" + i;
            new Thread(() -> {
                while (true) {
                    feed.lireNews(nom);
                    try { Thread.sleep(300); } catch (InterruptedException e) {}
                }
            }).start();
        }
    }
}