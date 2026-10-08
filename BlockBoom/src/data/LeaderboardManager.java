package data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * LeaderboardManager.java
 *
 * Responsible ONLY for loading/saving the leaderboard (top scores with
 * player names) to a small text file next to the program, so scores
 * survive closing and reopening the game. Each line in the file is
 * "name,score". The board keeps ONE entry per player name (their best
 * score). Kept separate from any UI class so the screens don't need to
 * know anything about file I/O.
 */
public class LeaderboardManager {
    private static final String FILE_NAME = "blockblast_leaderboard.csv";
    private static final int MAX_ENTRIES = 10;

    /** Best score of each player name, highest first. */
    public static List<ScoreEntry> load() {
        Map<String, ScoreEntry> best = new LinkedHashMap<>();
        File f = new File(FILE_NAME);
        if (f.exists()) {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), StandardCharsets.UTF_8))) {
                String line;
                while ((line = r.readLine()) != null) {
                    int comma = line.lastIndexOf(',');
                    if (comma < 0) continue;
                    String name = line.substring(0, comma).trim();
                    try {
                        int score = Integer.parseInt(line.substring(comma + 1).trim());
                        String key = name.toLowerCase();
                        ScoreEntry old = best.get(key);
                        if (old == null || score > old.getScore()) best.put(key, new ScoreEntry(name, score));
                    } catch (NumberFormatException ignored) { }
                }
            } catch (IOException ignored) { }
        }
        List<ScoreEntry> entries = new ArrayList<>(best.values());
        entries.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        return entries;
    }

    /** Records a finished game: keeps the player's best score, sorts and trims to the top entries. */
    public static void addScore(String name, int score) {
        List<ScoreEntry> entries = load();
        boolean found = false;
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getName().equalsIgnoreCase(name)) {
                if (score > entries.get(i).getScore()) entries.set(i, new ScoreEntry(name, score));
                found = true;
                break;
            }
        }
        if (!found) entries.add(new ScoreEntry(name, score));

        entries.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        if (entries.size() > MAX_ENTRIES) {
            entries = new ArrayList<>(entries.subList(0, MAX_ENTRIES));
        }
        save(entries);
    }

    /** The single best score ever recorded, or 0 if nobody has played yet. */
    public static int getTopScore() {
        List<ScoreEntry> entries = load();
        return entries.isEmpty() ? 0 : entries.get(0).getScore();
    }

    private static void save(List<ScoreEntry> entries) {
        try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FILE_NAME), StandardCharsets.UTF_8))) {
            for (ScoreEntry e : entries) {
                w.write(e.getName() + "," + e.getScore());
                w.newLine();
            }
        } catch (IOException ignored) {
            // saving the leaderboard is not critical to gameplay; ignore failures
        }
    }
}
