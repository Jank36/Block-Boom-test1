package view;

import view.components.RoundedButton;
import view.components.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * GameOverDialog.java
 *
 * Modal popup shown when a round ends. Shows the player's name and final
 * score and offers "Play Again" (runs the callback it was given) or
 * "Home" (back to the main menu). The score was already saved by
 * GamePanel before this dialog opens.
 */
public class GameOverDialog extends PopupDialog {
    public GameOverDialog(GameFrame frame, Component parent, String player, int score, Runnable onPlayAgain) {
        super(SwingUtilities.getWindowAncestor(parent), "Game Over", false);

        JLabel playerLabel = new JLabel(player);
        playerLabel.setFont(Theme.font(Font.BOLD, 14));
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel("Score : " + score);
        scoreLabel.setFont(Theme.font(Font.BOLD, 14));
        scoreLabel.setForeground(Theme.PURPLE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton againBtn = button("Play Again", Theme.GREEN);
        againBtn.addActionListener(e -> {
            dispose();
            onPlayAgain.run();
        });
        RoundedButton homeBtn = button("Home", Theme.RED);
        homeBtn.addActionListener(e -> {
            dispose();
            frame.goHome();
        });

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(18, 40, 20, 40));
        body.add(playerLabel);
        body.add(Box.createVerticalStrut(6));
        body.add(scoreLabel);
        body.add(Box.createVerticalStrut(16));
        body.add(againBtn);
        body.add(Box.createVerticalStrut(10));
        body.add(homeBtn);
        setBody(body);
    }

    private RoundedButton button(String text, Color color) {
        RoundedButton b = new RoundedButton(text, color);
        b.setPreferredSize(new Dimension(200, 40));
        b.setMaximumSize(new Dimension(200, 40));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        return b;
    }
}
