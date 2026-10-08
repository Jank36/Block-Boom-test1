package view;

import view.components.RoundedButton;
import view.components.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * SettingsDialog.java
 *
 * Small modal popup shown from the Game screen. Being a modal dialog,
 * it blocks input to the screen behind it while open, which is a natural
 * pause: while it's showing, no mouse events reach the game board.
 *
 *   - "X"    closes the popup and resumes exactly where you were.
 *   - "Home" returns to the Home / main-menu screen (the current round
 *            is abandoned and not saved).
 */
public class SettingsDialog extends PopupDialog {
    public SettingsDialog(GameFrame frame, Component parent) {
        super(SwingUtilities.getWindowAncestor(parent), "Setting", true);

        RoundedButton homeBtn = new RoundedButton("Home", Theme.RED);
        homeBtn.setPreferredSize(new Dimension(180, 42));
        homeBtn.addActionListener(e -> {
            dispose();
            frame.goHome();
        });

        JPanel body = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        body.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));
        body.add(homeBtn);
        setBody(body);
    }
}
