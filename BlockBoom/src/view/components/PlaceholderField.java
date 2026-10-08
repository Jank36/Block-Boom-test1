package view.components;


import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/** Text field that shows grey hint text while empty. */
public class PlaceholderField extends JTextField {
    private final String hint;

    public PlaceholderField(String hint) {
        this.hint = hint;
        setFont(Theme.font(Font.PLAIN, 13));
        setBorder(FieldHint.border());
        setPreferredSize(new Dimension(200, 36));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FieldHint.paint(this, g, hint, getText().isEmpty());
    }
}
