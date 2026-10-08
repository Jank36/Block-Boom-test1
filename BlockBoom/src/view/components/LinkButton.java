package view.components;


import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.Font;

/** Text-only blue button used for "Sign up" / "Login" links. */
public class LinkButton extends JButton {
    public LinkButton(String text) {
        super(text);
        setFont(Theme.font(Font.BOLD, 13));
        setForeground(new java.awt.Color(0x1E6BFF));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
