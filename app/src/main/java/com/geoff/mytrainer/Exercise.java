package com.geoff.mytrainer;

public class Exercise {
    public String name;
    private int weight;
    private int reps;
    private int sets;
    private int rest;

    public Exercise(String name, int weight, int reps, int sets, int rest){
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
        this.rest = rest;
    }
}
