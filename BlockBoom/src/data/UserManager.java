package data;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * UserManager.java
 *
 * Responsible ONLY for loading/saving user accounts to a small text file
 * next to the program. Each line is "username,email,passwordHash" with
 * every field URL-encoded so Thai names or commas are safe. Same style as
 * LeaderboardManager: static methods, no UI knowledge.
 */
public class UserManager {
    private static final String FILE_NAME = "blockblast_users.csv";

    public static User findByUsername(String username) {
        for (User u : loadAll()) {
            if (u.getUsername().equalsIgnoreCase(username)) return u;
        }
        return null;
    }

    public static User findByEmail(String email) {
        for (User u : loadAll()) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    public static void add(User user) {
        try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FILE_NAME, true), StandardCharsets.UTF_8))) {
            w.write(encode(user.getUsername()) + "," + encode(user.getEmail()) + "," + user.getPasswordHash());
            w.newLine();
        } catch (IOException ignored) {
            // account could not be saved; the user will simply not be able to log in later
        }
    }

    private static List<User> loadAll() {
        List<User> users = new ArrayList<>();
        File f = new File(FILE_NAME);
        if (!f.exists()) return users;
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 3) continue;
                users.add(new User(decode(parts[0]), decode(parts[1]), parts[2]));
            }
        } catch (IOException ignored) { }
        return users;
    }

    private static String encode(String s) {
        try { return URLEncoder.encode(s, "UTF-8"); }
        catch (UnsupportedEncodingException e) { throw new IllegalStateException(e); }
    }

    private static String decode(String s) {
        try { return URLDecoder.decode(s, "UTF-8"); }
        catch (UnsupportedEncodingException e) { throw new IllegalStateException(e); }
    }
}
