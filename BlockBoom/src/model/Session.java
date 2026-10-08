package model;

import data.User;

/**
 * Session.java
 *
 * Remembers who is logged in and which name is used on the leaderboard.
 * Plain state holder; GameFrame owns the single instance.
 */
public class Session {
    private User user;
    private String playerName = "";

    public void login(User user) {
        this.user = user;
        this.playerName = user.getUsername();
    }

    public void logout() {
        user = null;
        playerName = "";
    }

    public User getUser() { return user; }
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
}
