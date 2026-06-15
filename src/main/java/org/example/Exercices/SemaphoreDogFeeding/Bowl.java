package org.example.Exercices.SemaphoreDogFeeding;

import java.util.concurrent.Semaphore;

public class Bowl {

    boolean empty = false;


    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

}