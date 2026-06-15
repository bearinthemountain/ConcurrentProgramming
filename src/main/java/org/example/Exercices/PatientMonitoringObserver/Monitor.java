package org.example.Exercices.PatientMonitoringObserver;

/**
 * INTERFACE DU SUJET (Observable)
 * Cette interface définit les méthodes pour s'abonner, se désabonner et notifier les observateurs.
 * En examen, c'est la structure classique du Pattern Observer.
 */
public interface Monitor {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    
    // On peut avoir notifyObservers sans paramètre (Pull model)
    // ou avec paramètre (Push model) pour envoyer directement le problème détecté.
    void notifyObservers();

    /**
     * INTERFACE DE L'OBSERVATEUR (Subscriber)
     * C'est elle que les employés médicaux implémenteront pour recevoir les alertes.
     */
    public interface Observer {
        // En examen : Adapter les paramètres selon le besoin.
        // Ici, on passe le PatientMonitoring (pour la position/infos) et le Problem détecté.
        void update(PatientMonitoring pm, Problem problem);
    }
}

