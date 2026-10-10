package view;

import model.Session;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {
    public static final String LOGIN = "login";
    public static final String SIGN_UP = "signup";
    public static final String HOME = "home";
    public static final String GAME = "game";
    public static final String LEADERBOARD = "leaderboard";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final Session session = new Session();

    private final LoginPanel loginPanel;
    private final SignUpPanel signUpPanel;
    private final HomePanel homePanel;
    private final GamePanel gamePanel;
    private final LeaderboardPanel leaderboardPanel;

    public GameFrame() {
        super("Block Boom - Java Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        loginPanel = new LoginPanel(this);
        signUpPanel = new SignUpPanel(this);
        homePanel = new HomePanel(this);
        gamePanel = new GamePanel(this);
        leaderboardPanel = new LeaderboardPanel(this);

        cards.add(loginPanel, LOGIN);
        cards.add(signUpPanel, SIGN_UP);
        cards.add(homePanel, HOME);
        cards.add(gamePanel, GAME);
        cards.add(leaderboardPanel, LEADERBOARD);

        add(cards);
        pack();
        setLocationRelativeTo(null);
        showLogin();
        setVisible(true);
    }

    public Session getSession() {
        return session;
    }

    /** Show the Login screen (fields cleared). */
    public void showLogin() {
        loginPanel.reset();
        cardLayout.show(cards, LOGIN);
    }

    /** Show the Sign up screen (fields cleared). */
    public void showSignUp() {
        signUpPanel.reset();
        cardLayout.show(cards, SIGN_UP);
    }

    /** Forget the current user and go back to Login. */
    public void logout() {
        session.logout();
        showLogin();
    }

    /** Go back to the main menu. */
    public void goHome() {
        homePanel.refresh();
        cardLayout.show(cards, HOME);
    }

    /** Start a fresh game for the given player name and switch to the Game screen. */
    public void startGame(String playerName) {
        session.setPlayerName(playerName);
        gamePanel.startNewGame(playerName);
        cardLayout.show(cards, GAME);
    }

    /** Refresh and show the leaderboard screen. */
    public void openLeaderboard() {
        leaderboardPanel.refresh();
        cardLayout.show(cards, LEADERBOARD);
    }
}
