package com.numberguessing;


import java.util.Random;

public class GameService {

    private Random random = new Random();
    private int number;
    private int tries;
    private boolean won;

    public void startNewGame(){
        number = random.nextInt(100) + 1;
        tries = 0;
        won = false;
    }

    public String checkGuess(int guess){
        if(guess > 100 || guess < 1){
            return "INVALID";
        }
        if(won){
            return "ALREADY_WON";
        }

        tries++;

        if(guess == number){
            won = true;
            return "CORRECT";
        }
        else if(guess > number){
            return "TOO_HIGH";
        }
        else{
            return "TOO_LOW";
        }
    }

    public int getTries() {
        return tries;
    }

    public boolean isWon() {
        return won;
    }
}
