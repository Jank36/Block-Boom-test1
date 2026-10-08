package view;

import view.components.Theme;
import view.components.RoundedButton;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;

/** Base for the in-game popups: purple title bar, optional close (X) button, white body. */
abstract class PopupDialog extends JDialog {
    private final JPanel root = new JPanel(new BorderLayout());

    PopupDialog(Window owner, String title, boolean closable) {
        super(owner, ModalityType.APPLICATION_MODAL);
        setUndecorated(true);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.PURPLE);
        header.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 6));
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(Theme.font(Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel, BorderLayout.CENTER);

        if (closable) {
            RoundedButton close = new RoundedButton("X", Theme.RED);
            close.setPreferredSize(new Dimension(30, 26));
            close.addActionListener(e -> dispose());
            header.add(close, BorderLayout.EAST);
        } else {
            header.add(javax.swing.Box.createHorizontalStrut(30), BorderLayout.EAST);
        }

        root.setBorder(BorderFactory.createLineBorder(Theme.PURPLE, 2));
        root.add(header, BorderLayout.NORTH);
        setContentPane(root);
    }

    /** Sets the white content area, then sizes and centres the dialog. */
    protected void setBody(JComponent body) {
        body.setBackground(Color.WHITE);
        root.add(body, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
}
