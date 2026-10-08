package view;

import model.AuthService;
import view.components.*;

import javax.swing.*;
import java.awt.*;

/**
 * SignUpPanel.java
 *
 * The Sign up screen: username, email, password and confirmation.
 * Validation and saving are done by AuthService; this class only reads
 * the fields and shows the result.
 */
public class SignUpPanel extends GradientPanel {
    private final PlaceholderField usernameField = new PlaceholderField("Username");
    private final PlaceholderField emailField = new PlaceholderField("Email");
    private final PlaceholderPasswordField passwordField = new PlaceholderPasswordField("Password");
    private final PlaceholderPasswordField confirmField = new PlaceholderPasswordField("Confirm Password");
    private final JLabel errorLabel = new JLabel(" ");
    private final GameFrame frame;

    public SignUpPanel(GameFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(GamePanel.PANEL_W, GamePanel.PANEL_H));
        setLayout(new GridBagLayout());

        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setPreferredSize(new Dimension(320, 560));
        column.add(new LogoLabel());
        column.add(Box.createVerticalStrut(10));
        column.add(buildCard());
        column.add(Box.createVerticalGlue());
        column.add(buildFooter());
        Layouts.alignLeft(column);
        add(column);
    }

    private JPanel buildCard() {
        RoundedPanel card = new RoundedPanel(new Color(255, 255, 255, 235), 18);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 340));

        JLabel title = new JLabel("Sign up");
        title.setFont(Theme.font(Font.BOLD, 18));
        JLabel subtitle = new JLabel("Create your account");
        subtitle.setFont(Theme.font(Font.PLAIN, 12));
        subtitle.setForeground(Theme.TEXT_MUTED);

        errorLabel.setForeground(Theme.RED);
        errorLabel.setFont(Theme.font(Font.PLAIN, 12));

        RoundedButton createButton = new RoundedButton("Create Account", Theme.GREEN);
        createButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        createButton.addActionListener(e -> doRegister());
        confirmField.addActionListener(e -> doRegister());

        card.add(title);
        card.add(subtitle);
        card.add(Box.createVerticalStrut(12));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(8));
        card.add(emailField);
        card.add(Box.createVerticalStrut(8));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(8));
        card.add(confirmField);
        card.add(Box.createVerticalStrut(6));
        card.add(errorLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(createButton);
        Layouts.alignLeft(card);
        return card;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        footer.setOpaque(false);
        footer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel text = new JLabel("Already have an account?");
        text.setFont(Theme.font(Font.PLAIN, 12));
        LinkButton login = new LinkButton("Login");
        login.addActionListener(e -> frame.showLogin());
        footer.add(text);
        footer.add(login);
        return footer;
    }

    private void doRegister() {
        String error = AuthService.register(usernameField.getText(), emailField.getText(),
                new String(passwordField.getPassword()), new String(confirmField.getPassword()));
        if (error != null) {
            errorLabel.setText(error);
            return;
        }
        JOptionPane.showMessageDialog(this, "Account created! Please log in.");
        frame.showLogin();
    }

    /** Clears the form. Called by GameFrame right before showing this screen. */
    public void reset() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmField.setText("");
        errorLabel.setText(" ");
    }
}
