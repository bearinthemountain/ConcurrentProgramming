package org.example.Exercices.PatientMonitoringObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASSE CONCRETE DU SUJET (Observable)
 * Elle implémente l'interface Monitor.
 * Elle maintient son état interne (pression, oxymétrie, position) ainsi que la liste des abonnés.
 */
public class PatientMonitoring implements Monitor {
    
    // 1. Attributs d'identification et de seuils
    private final int id;
    private final int maxBloodPressure;  // Pression artérielle max tolérée (ex: 140)
    private final int minPulseOximetry;   // Oxymétrie minimale tolérée (ex: 85 - c'est un seuil critique bas)

    // 2. État courant du patient (qui va fluctuer)
    private int bloodPressure;
    private int pulseOximetry;
    private int position;

    // 3. La liste magique des observateurs (les employés médicaux à notifier)
    private final List<Observer> observers;

    /**
     * Constructeur
     * Initialise le patient avec ses limites et des constantes de santé de base.
     */
    public PatientMonitoring(int id, int maxBloodPressure, int minPulseOximetry) {
        this.id = id;
        this.maxBloodPressure = maxBloodPressure;
        this.minPulseOximetry = minPulseOximetry;
        
        // Initialiser avec des valeurs saines par défaut pour éviter des alertes immédiates
        this.bloodPressure = 120; // Normal
        this.pulseOximetry = 95;  // Normal
        this.position = 1;        // Position initiale
        
        this.observers = new ArrayList<>();
    }

    // ==========================================
    // METHODES DE L'INTERFACE MONITOR (SUJET)
    // ==========================================

    @Override
    public void registerObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notification des observateurs.
     * Cette méthode vérifie d'abord si le patient a un problème.
     * Si oui, elle avertit tous les observateurs avec le problème en question (Push).
     */
    @Override
    public void notifyObservers() {
        Problem problem = checkProblem();
        
        // EXAMEN : On ne notifie que s'il y a réellement un problème de santé.
        if (problem != Problem.NO_PROBLEM) {
            // Utilisation d'une copie pour éviter les ConcurrentModificationException 
            // si un observateur se désinscrit pendant la notification.
            List<Observer> copy = new ArrayList<>(observers);
            for (Observer observer : copy) {
                observer.update(this, problem);
            }
        }
    }

    // ==========================================
    // LOGIQUE METIER ET SETTERS
    // ==========================================

    /**
     * Evalue l'état de santé actuel du patient.
     * Retourne le type de problème détecté selon les seuils.
     */
    private Problem checkProblem() {
        // Si la pression sanguine dépasse le max autorisé -> Problème de pression
        if (this.bloodPressure > this.maxBloodPressure) {
            return Problem.BLOOD_PRESSURE;
        }
        // Si la saturation en oxygène tombe en-dessous du minimum -> Problème d'oxymétrie
        if (this.pulseOximetry < this.minPulseOximetry) {
            return Problem.OXIMETRY;
        }
        return Problem.NO_PROBLEM;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
        // EXAMEN : Chaque modification d'état sensible doit déclencher une notification !
        notifyObservers();
    }

    public void setPulseOximetry(int pulseOximetry) {
        this.pulseOximetry = pulseOximetry;
        notifyObservers();
    }

    public void setPosition(int position) {
        this.position = position;
        // Si le patient change de position alors qu'il est en détresse, il faut ré-alerter !
        notifyObservers();
    }

    // Getters pour que l'observateur puisse lire l'état du patient (modèle Pull)
    
    public int getId() {
        return id;
    }

    public int getBloodPressure() {
        return bloodPressure;
    }

    public int getPulseOximetry() {
        return pulseOximetry;
    }

    public int getPosition() {
        return position;
    }
}
