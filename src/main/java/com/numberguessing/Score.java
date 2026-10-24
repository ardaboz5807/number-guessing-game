package com.numberguessing;

public class Score {

    private int id;
    private String playerName;
    private int attempts;
    private String playedAt;

    public Score(int id, String playerName, int attempts, String playedAt) {
        this.id = id;
        this.playerName = playerName;
        this.attempts = attempts;
        this.playedAt = playedAt;
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getPlayedAt() {
        return playedAt;
    }
}
