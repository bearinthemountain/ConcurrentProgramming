package org.example.Exercices.AdventureGameStrategy;

public abstract class Character {

    protected WeaponBehavior weapon;

    public Character(WeaponBehavior weapon){
        this.weapon = weapon;
    }

    public void setWeapon(WeaponBehavior w) {
        this.weapon = w;
    }

    public void fight(){
        if (weapon != null) {
            System.out.print(getClass().getSimpleName() + " is ");
            weapon.useWeapon();
        } else {
            System.out.println(getClass().getSimpleName() + " has no weapon!");
        }
    }

}
