package org.example.Exercices.SemaphoreDogFeeding;

import java.util.concurrent.Semaphore;

public class Dogs implements Runnable{
    private String name;
    private Bowl[] bowls;
    private Semaphore[] semaphores;


    public Dogs(String name, Bowl[] bowls, Semaphore[] semaphores) {
        this.name = name;
        this.bowls = bowls;
        this.semaphores = semaphores;
    }

    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(1000);

                for (int i = 0; i < bowls.length; i++) {

                    if (semaphores[i].tryAcquire() == true) {
                        System.out.println(name + "je mange dans la gamelle" + i);
                        bowls[i].setEmpty(true);//on mange
                    }
                }
            }catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }


                }
            }
        }










