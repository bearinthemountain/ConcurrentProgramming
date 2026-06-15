package org.example.Exercices.AdventureGameStrategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Adventure Game Simulator Started ---");

        // 1. Create a Troll using a knife
        Character troll = new Troll(new KnifeBehavior());
        troll.fight();

        // Dynamically change Troll's weapon to an axe
        System.out.println("Troll finds an axe!");
        troll.setWeapon(new AxeBehavior());
        troll.fight();
        System.out.println();

        // 2. Create a King using a sword
        Character king = new King(new SwordBehavior());
        king.fight();
        System.out.println();

        // 3. Create a Queen using a bow and arrow
        Character queen = new Queen(new BowAndArrowBehavior());
        queen.fight();
        System.out.println();

        // 4. Create a Knight using a sword
        Character knight = new Knight(new SwordBehavior());
        knight.fight();

        // Dynamically change Knight's weapon to a bow and arrow
        System.out.println("Knight switches to bow and arrow!");
        knight.setWeapon(new BowAndArrowBehavior());
        knight.fight();

        System.out.println("--- Adventure Game Simulator Finished ---");
    }
}