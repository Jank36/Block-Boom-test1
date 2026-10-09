package model;

import data.User;
import data.UserManager;

/**
 * AuthService.java
 *
 * Rules for Sign up and Login (validation + password check). Persistence
 * is delegated to UserManager and hashing to PasswordHasher, so this
 * class contains only the decision-making.
 */
public class AuthService {
    private AuthService() { }


    public static String register(String username, String password, String confirm) {
        username = username.trim();
        if (username.isEmpty()  || password.isEmpty()) return "Please fill in all fields.";
        if (password.length() < 4) return "Password must be at least 4 characters.";
        if (!password.equals(confirm)) return "Passwords do not match.";
        if (UserManager.findByUsername(username) != null) return "Username already taken.";
        UserManager.add(new User(username, password));
        return null;
    }

    /** Accepts username OR email. Returns the user, or null if the credentials are wrong. */
    public static User login(String identifier, String password) {
        identifier = identifier.trim();
        User user = UserManager.findByUsername(identifier);
        if (user == null) return null;
        return user.getPassword().equals(password) ? user : null;
    }
}
