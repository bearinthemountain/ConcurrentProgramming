package org.example.Exercices.PatientMonitoringObserver;

public class Main {
    public static void main(String[] args) {
        PatientMonitoring pm = new PatientMonitoring(1, 140,85);
        MedicalEmployee jean = new MedicalEmployee("Jean    ", pm);
        MedicalEmployee pauline = new MedicalEmployee("Pauline ", pm);
        MedicalEmployee matthieu = new MedicalEmployee("Matthieu", pm);
        MedicalEmployee symeon = new MedicalEmployee("Symeon  ", pm);
        pm.setBloodPressure(110); pm.setPosition(3);
        pm.setPulseOximetry(90); pm.setPulseOximetry(70);
        pm.setPosition(7); pm.setBloodPressure(150);
        pm.removeObserver(matthieu);
        pm.setBloodPressure(145);
        pm.setPosition(9); 
    } 
}

/*
Voici le mécanisme étape par étape :

  ### 1. La modification de l'état (les Setters)

  Dès qu'une valeur change via un setter ( setBloodPressure ,  setPulseOximetry  ou  setPosition ), le sujet appelle immédiatement sa méthode de notification :

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
        notifyObservers(); // <--- L'appel déclencheur
    }

  ### 2. L'évaluation de l'état (PatientMonitoring.java)


  La méthode  notifyObservers()  commence par exécuter la méthode privée  checkProblem()  pour analyser si les valeurs dépassent les seuils critiques :

  • Si la tension artérielle est supérieure à 140 (seuil max) → renvoie  Problem.BLOOD_PRESSURE .
  • Si l'oxymétrie est inférieure à 85 (seuil min) → renvoie  Problem.OXIMETRY .
  • Sinon → renvoie  Problem.NO_PROBLEM .

  ### 3. La diffusion aux abonnés (le Pattern Observer)

  Si un problème est détecté, le sujet parcourt sa liste d'observateurs et appelle leur méthode  update()  :

    if (problem != Problem.NO_PROBLEM) {
        for (Observer observer : observers) {
            observer.update(this, problem); // <--- L'alerte est envoyée à chaque employé
        }
    }

  ### 4. La réception de l'alerte

  Enfin, chaque MedicalEmployee.java reçoit l'appel dans sa méthode  update()  et affiche le message d'alerte sur sa console.
  ──────
  Résumé : Explication détaillée du processus de déclenchement des alertes à partir des setters de PatientMonitoring.java jusqu'à la réception par les MedicalEmployee.java.




 */

