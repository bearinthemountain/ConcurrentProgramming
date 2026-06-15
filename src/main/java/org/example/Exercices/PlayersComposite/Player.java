package org.example.Exercices.PlayersComposite;

// la feuille
public class Player extends PlayerComponent {
    private int id;

    public Player(int id){this.id = id;}

    @Override
    public void enterField() {
        System.out.println("Player" + id + "enters the field.");
    }

    @Override
    public void cry() {
        System.out.println("Player" + id + "is crying");
    }

    @Override
    public void simulateInjury() {
        System.out.println("Player" + id + "is simulated");

    }
}
