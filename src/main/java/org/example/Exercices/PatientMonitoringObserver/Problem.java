package org.example.Exercices.PatientMonitoringObserver;

/**
 * ENUMERATION DES PROBLEMES DETECTES
 * Représente l'état de santé / le type d'anomalie du patient.
 * En Java, un  enum  (énumération) est un type spécial qui permet de définir un groupe de constantes (valeurs fixes qui ne changent pas).
 *
 *   Dans votre exercice, l'énumération Problem.java représente l'état de santé du patient à un instant donné :
 *
 *   1.  NO_PROBLEM  : Tout va bien. Les constantes physiologiques du patient (tension et oxygène) sont dans les normes.
 *   2.  BLOOD_PRESSURE  : Alerte de tension artérielle. La pression sanguine a dépassé le seuil maximal autorisé (140).
 *   3.  OXIMETRY  : Alerte d'oxygénation. Le taux d'oxygène dans le sang (oxymétrie de pouls) est descendu en dessous du seuil minimal autorisé (85).
 *
 *   ### Utilité pour le Pattern Observer
 *
 *   Lorsqu'un problème survient, le sujet (PatientMonitoring.java) évalue les valeurs et transmet cette constante de type Problem.java aux observateurs (MedicalEmployee.java) via la méthode
 * update .
 *   Cela évite d'envoyer de simples chaînes de caractères (Strings) sujettes aux fautes de frappe et permet un traitement propre avec des conditions (comme des structures  switch  ou  if
 *   ).
 *   ──────
 *   Résumé : Explication du rôle de l'énumération Problem.java et de sa pertinence pour le Pattern Observer.
 */
public enum Problem {
    NO_PROBLEM, 
    BLOOD_PRESSURE, 
    OXIMETRY;
}
