package view.components;

import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Container;

/**
 * Layouts.java
 *
 * Tiny helper for BoxLayout panels: Swing components have different
 * default alignments (labels left, buttons centre...), which makes a
 * vertical stack look crooked. alignLeft() makes every child agree.
 */
public final class Layouts {
    private Layouts() { }

    public static void alignLeft(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JComponent) ((JComponent) c).setAlignmentX(Component.LEFT_ALIGNMENT);
        }
    }
}
