import view.GameFrame;

import javax.swing.SwingUtilities;

/**
 * Main.java
 * Entry point. Starts the Swing UI on the Event Dispatch Thread
 * (standard practice: all Swing components must be created/updated
 * on the EDT, not on the main thread).
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
