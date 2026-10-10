package view.components;

import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Container;


public final class Layouts {
    private Layouts() { }

    public static void alignLeft(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JComponent) ((JComponent) c).setAlignmentX(Component.LEFT_ALIGNMENT);
        }
    }
}
