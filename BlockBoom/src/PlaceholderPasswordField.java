package view.components;


import javax.swing.JPasswordField;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/** Password field that shows grey hint text while empty. */
public class PlaceholderPasswordField extends JPasswordField {
    private final String hint;

    public PlaceholderPasswordField(String hint) {
        this.hint = hint;
        setFont(Theme.font(Font.BOLD, 15));
        setForeground(Theme.FIELD_TEXT);
        setBorder(FieldHint.border());
        setPreferredSize(new Dimension(200, 50));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FieldHint.paint(this, g, hint, getPassword().length == 0);
    }
}
