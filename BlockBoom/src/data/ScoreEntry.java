package data;

/**
 * ScoreEntry.java
 *
 * Immutable data holder for one leaderboard row: a player's name and
 * the score they achieved. Deliberately tiny — it has no behavior,
 * just data (a simple ADT).
 */
public class ScoreEntry {
    private final String name;
    private final int score;

    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
}
