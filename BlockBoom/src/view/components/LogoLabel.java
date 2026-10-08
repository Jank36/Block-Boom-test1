package view.components;

import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/** "Block Boom" title drawn as outlined text. */
public class LogoLabel extends JComponent {
    public LogoLabel() {
        setPreferredSize(new Dimension(320, 120));
        setMaximumSize(new Dimension(320, 120));
        setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = getWidth() / 2;
        drawOutlined(g2, "Block", new Font("Arial Black", Font.BOLD, 54), cx, 52, new Color(0xFFC800));
        drawOutlined(g2, "Boom", new Font("Arial Black", Font.BOLD, 58), cx, 108, new Color(0x19B8FF));
        g2.dispose();
    }

    private void drawOutlined(Graphics2D g2, String text, Font font, int cx, int baseY, Color fill) {
        Shape glyphs = font.createGlyphVector(g2.getFontRenderContext(), text).getOutline();
        Rectangle2D b = glyphs.getBounds2D();
        Shape s = AffineTransform.getTranslateInstance(cx - b.getWidth() / 2 - b.getX(), baseY)
                .createTransformedShape(glyphs);
        g2.setStroke(new BasicStroke(9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(0x1B2A8A));
        g2.draw(s);
        g2.setColor(fill);
        g2.fill(s);
    }
}
