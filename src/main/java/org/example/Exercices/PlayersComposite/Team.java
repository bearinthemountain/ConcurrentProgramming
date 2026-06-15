package org.example.Exercices.PlayersComposite;

import java.util.ArrayList;
import java.util.List;

/*
Si tu utilises une simple liste, ton code main devient "sale" et rigide :

Tu perds la hiérarchie : Dans ton exercice,
une équipe peut contenir des joueurs et d'autres équipes (team2.add(team1)).
Une simple liste List<Player> ne peut pas contenir des Team. Le Composite permet cette structure en arbre infinie.

Tu perds la transparence : Avec une liste, tu devrais toujours tester le type :
if (element instanceof Team) { ... } else { ... }. Le Composite cache cette complexité :
tu appelles cry() sur un objet et tu te fiches de savoir si c'est un joueur ou une équipe de 100 personnes.


 */

public class Team extends PlayerComponent{
    private List<PlayerComponent> members = new ArrayList<>();

    @Override
    public void add(PlayerComponent c) {
        members.add(c);
    }

    @Override
    public void remove(PlayerComponent c) {
        members.remove(c);
    }

    @Override
    public void enterField() {
        for (PlayerComponent m : members) m.enterField();
    }

    @Override
    public void cry() {
        for (int i = 0; i< members.size();i++){
            members.get(i).cry();
        }
    }

    @Override
    public void simulateInjury() {
        for(PlayerComponent m : members) m.simulateInjury();
    }
}
