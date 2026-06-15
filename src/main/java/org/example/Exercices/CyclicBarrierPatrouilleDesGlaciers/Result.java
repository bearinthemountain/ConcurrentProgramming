package org.example.Exercices.CyclicBarrierPatrouilleDesGlaciers;

public class Result implements Comparable<Result>{

    private final int time;
    private final String team;

    public Result(String team, int time) {
        this.time = time;         this.team = team;     }

    public int getTime() {
        return time;     }

    public String getTeam() {
        return team;     }


    @Override
    public int compareTo(Result result) {
        return this.getTime() - result.getTime();     } }