package view;

import data.LeaderboardManager;
import data.ScoreEntry;
import view.components.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * LeaderboardPanel.java
 *
 * Shows the top scores (rank, name, score) loaded from
 * LeaderboardManager, one row per player. The top three rows are tinted
 * gold / silver / bronze. A Home button in the header returns to the
 * main menu.
 */
public class LeaderboardPanel extends GradientPanel {
    private static final Color GOLD = new Color(0xF2C94C);
    private static final Color SILVER = new Color(0xC3CEDD);
    private static final Color BRONZE = new Color(0xD1A073);

    private final DefaultTableModel model;
    private boolean hasData = false;

    public LeaderboardPanel(GameFrame frame) {
        setPreferredSize(new Dimension(GamePanel.PANEL_W, GamePanel.PANEL_H));
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 20, 24, 20));

        RoundedPanel titlePill = new RoundedPanel(Theme.YELLOW, 12);
        titlePill.setLayout(new BorderLayout());
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(Theme.font(Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        titlePill.add(title);

        RoundedButton homeBtn = new RoundedButton("Home", Theme.PURPLE);
        homeBtn.setPreferredSize(new Dimension(80, 40));
        homeBtn.addActionListener(e -> frame.goHome());

        JPanel header = new JPanel(new BorderLayout(10, 0));
        header.setOpaque(false);
        header.add(titlePill, BorderLayout.CENTER);
        header.add(homeBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"No.", "Name", "Score"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                Color bg = Color.WHITE;
                if (hasData) {
                    if (row == 0) bg = GOLD;
                    else if (row == 1) bg = SILVER;
                    else if (row == 2) bg = BRONZE;
                }
                c.setBackground(bg);
                c.setForeground(Theme.TEXT_DARK);
                return c;
            }
        };
        table.setFont(Theme.font(Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setEnabled(false);
        table.setShowGrid(false);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, center);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(Theme.NAVY);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(Theme.font(Font.BOLD, 13));
        table.getTableHeader().setDefaultRenderer(headerRenderer);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(170);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.PURPLE, 3));
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);
    }

    /** Reloads the table from disk. Call this right before showing the screen. */
    public void refresh() {
        model.setRowCount(0);
        List<ScoreEntry> entries = LeaderboardManager.load();
        hasData = !entries.isEmpty();
        int rank = 1;
        for (ScoreEntry entry : entries) {
            model.addRow(new Object[]{String.valueOf(rank++), entry.getName(), String.valueOf(entry.getScore())});
        }
        if (!hasData) {
            model.addRow(new Object[]{"-", "No information available", "-"});
        }
    }
}
