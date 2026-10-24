package com.numberguessing;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

public class Database {

    private static final String url = "jdbc:sqlite:scores.db";

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS scores (id INTEGER PRIMARY KEY AUTOINCREMENT, player_name TEXT NOT NULL, attempts INTEGER NOT NULL CHECK (attempts > 0), played_at TEXT NOT NULL)";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){

            stmt.executeUpdate(sql);
            System.out.println("Tabelle erfolgreich initialisiert!");

        }
        catch(SQLException e){
            throw new IllegalStateException("Fehler beim Initialisieren der Tabelle!",e);
        }
    }

    public void saveScore(String playerName, int attempts) {
        String sql = "INSERT INTO scores (player_name, attempts, played_at) VALUES (?,?,?)";

        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException("Spielername darf nicht leer sein!");
        }

        if (attempts <= 0) {
            throw new IllegalArgumentException("Anzahl der Versuche muss größer als 0 sein!");
        }

        playerName = playerName.trim();

        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,playerName);
            stmt.setInt(2,attempts);
            stmt.setString(3, Instant.now().toString());

            stmt.executeUpdate();

        }
        catch(SQLException e){
            throw new IllegalStateException("Fehler beim Speichern der Werte!",e);
        }
    }

    public ArrayList<Score> getHighScores() {
        String sql = "SELECT * FROM scores ORDER BY attempts ASC";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            ArrayList<Score> res = new ArrayList<>();
            while(rs.next()){
                res.add(new Score(rs.getInt("id"),rs.getString("player_name"),rs.getInt("attempts"),rs.getString("played_at")));
            }
            return res;
        }
        catch(SQLException e){
            throw new IllegalStateException("Fehler beim Laden der Highscores!",e);
        }
    }
}
