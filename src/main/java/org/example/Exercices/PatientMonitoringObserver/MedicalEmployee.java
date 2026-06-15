package org.example.Exercices.PatientMonitoringObserver;

/**
 * OBSERVATEUR CONCRET (Subscriber)
 * Il implémente l'interface Monitor.Observer.
 * Chaque employé s'abonne à un patient pour recevoir ses alertes de santé en temps réel.
 */
public class MedicalEmployee implements Monitor.Observer {
    
    private final String name;

    /**
     * Constructeur.
     * Enregistre automatiquement l'employé auprès du PatientMonitoring (Sujet).
     */
    public MedicalEmployee(String name, PatientMonitoring pm) {
        this.name = name;
        if (pm != null) {
            // EXAMEN : L'auto-enregistrement simplifie la création dans le Main.
            pm.registerObserver(this);
        }
    }

    /**
     * Méthode de rappel (callback) appelée par le sujet lors d'un problème.
     * C'est ici que l'employé réagit à l'alerte.
     */
    @Override
    public void update(PatientMonitoring pm, Problem problem) {
        // Modèle hybride Push/Pull :
        // - Push : On reçoit le 'problem' en paramètre.
        // - Pull : On va chercher la position et l'ID du patient via les getters de 'pm'.
        
        System.out.println(
            name + " received alert -> Patient ID: " + pm.getId() + 
            " | Problem: " + problem + 
            " | Position: " + pm.getPosition()
        );
    }
    
    public String getName() {
        return name;
    }
}
