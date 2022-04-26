package com.tasca02.sprint05.models;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class Game {
    
    private int winningSum = 7;
    private int numSides = 6;
    private Random random = new Random();

    public Game(){}

    public Game(int winningSum, int numSides) {
        this.winningSum = winningSum;
        this.numSides = numSides;
    }

    public Toss generateToss() {
        int a = random.nextInt(1, numSides + 1);
        int b = random.nextInt(1, numSides + 1);

        random.setSeed(System.nanoTime() * a);
        return new Toss((byte)a, (byte)b);
    }

    public int getWinningSum() {
        return winningSum;
    }

    public void setWinningSum(int winningSum) {
        this.winningSum = winningSum;
    }

    public int getNumSides() {
        return numSides;
    }

    public void setNumSides(int numSides) {
        this.numSides = numSides;
    }
    
    
}
