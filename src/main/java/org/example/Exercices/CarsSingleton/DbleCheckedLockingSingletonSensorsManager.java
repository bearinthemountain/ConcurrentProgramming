package org.example.Exercices.CarsSingleton;


import java.util.ArrayList;
import java.util.List;

public class DbleCheckedLockingSingletonSensorsManager {
        // MOT-CLÉ OBLIGATOIRE POUR QUE ÇA FONCTIONNE !
        private volatile static DbleCheckedLockingSingletonSensorsManager instanceUnique;

        private List<Sensor> listeCapteurs;

        private DbleCheckedLockingSingletonSensorsManager() {
            listeCapteurs = new ArrayList<>();
            System.out.println("Démarrage du systpme central de gestion des capteurs...");
        }


        public static DbleCheckedLockingSingletonSensorsManager getInstance() {
            // 1er Check : On vérifie sans bloquer (pour la rapidité)
            if (instanceUnique == null) {

                // On bloque uniquement la première fois (pour la sécurité)
                synchronized (DbleCheckedLockingSingletonSensorsManager.class) {//verrou

                    // 2ème Check : Une fois dans la zone sécurisée, on revérifie
                    if (instanceUnique == null) {
                        instanceUnique = new DbleCheckedLockingSingletonSensorsManager();
                    }
                }
            }
            return instanceUnique;
        }
        public void addSensor(Sensor capteur){
            listeCapteurs.add(capteur);
            System.out.println("un nouveau capteur a été branché au système");
            }

        public void printState(){
            System.out.println("---Lecture des capteurs----");
            for (Sensor capteur : listeCapteurs){
                System.out.println("Donnée reçue : " + capteur.getData());
            }
                System.out.println("----------------------------");
            }
    }


