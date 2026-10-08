package model;

import java.awt.Color;

/**
 * Board.java
 *
 * Model class representing the 8x8 game grid.
 * Responsible ONLY for grid state and the rules for placing pieces
 * and clearing full rows/columns (Single Responsibility Principle).
 * Knows nothing about drawing, mouse input, or scoring.
 */
public class Board {
    public static final int SIZE = 8;

    // null = empty cell, non-null = color of the block occupying it
    private final Color[][] grid = new Color[SIZE][SIZE];

    /** True if every cell the piece would occupy is empty and inside the board. */
    public boolean canPlace(Piece piece, int row, int col) {
        for (int[] cell : piece.getCells()) {
            int r = row + cell[0];
            int c = col + cell[1];
            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE) return false;
            if (grid[r][c] != null) return false;
        }
        return true;
    }

    /** Places the piece on the board. Caller must check canPlace() first. */
    public void place(Piece piece, int row, int col) {
        for (int[] cell : piece.getCells()) {
            grid[row + cell[0]][col + cell[1]] = piece.getColor();
        }
    }

    /** Clears every fully-filled row and column. Returns the number of lines cleared. */
    public int clearFullLines() {
        boolean[] fullRows = new boolean[SIZE];
        boolean[] fullCols = new boolean[SIZE];
        int cleared = 0;

        for (int r = 0; r < SIZE; r++) {
            boolean full = true;
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] == null) { full = false; break; }
            }
            fullRows[r] = full;
            if (full) cleared++;
        }

        for (int c = 0; c < SIZE; c++) {
            boolean full = true;
            for (int r = 0; r < SIZE; r++) {
                if (grid[r][c] == null) { full = false; break; }
            }
            fullCols[c] = full;
            if (full) cleared++;
        }

        for (int r = 0; r < SIZE; r++) {
            if (fullRows[r]) {
                for (int c = 0; c < SIZE; c++) grid[r][c] = null;
            }
        }
        for (int c = 0; c < SIZE; c++) {
            if (fullCols[c]) {
                for (int r = 0; r < SIZE; r++) grid[r][c] = null;
            }
        }
        return cleared;
    }

    public Color getColor(int row, int col) {
        return grid[row][col];
    }

    /** True if the given piece cannot be placed ANYWHERE on the current board. */
    public boolean hasNoValidMove(Piece piece) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (canPlace(piece, r, c)) return false;
            }
        }
        return true;
    }

    public void reset() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                grid[r][c] = null;
    }
}
