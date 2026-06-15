package org.example.Exercices.PlayersComposite;

public abstract class PlayerComponent {

    /*
    La UnsupportedOperationException est ton "filet de sécurité" :
    elle évite aux feuilles de devoir écrire du code inutile pour des actions de
    gestion de groupe qu'elles ne peuvent pas accomplir.

     */
    public void add(PlayerComponent c){throw new UnsupportedOperationException();}
    public void remove(PlayerComponent c){throw new UnsupportedOperationException();}

    public abstract void enterField();
    public abstract void cry();
    public abstract void simulateInjury();
}
