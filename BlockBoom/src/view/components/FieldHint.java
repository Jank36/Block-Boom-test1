package view.components;


import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/** Shared look + placeholder painting for the text fields. */
final class FieldHint {
    private FieldHint() {}

    static Border border() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.FIELD_BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10));
    }

    static void paint(JTextComponent field, Graphics g, String hint, boolean empty) {
        if (!empty || field.isFocusOwner()) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(new Color(0x9AA8C2));
        g2.setFont(field.getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = field.getInsets().left;
        int y = (field.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(hint, x, y);
        g2.dispose();
    }
}
