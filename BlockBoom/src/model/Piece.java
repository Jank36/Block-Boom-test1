package model;

import java.awt.Color;

/**
 * Piece.java
 *
 * Immutable model of a single block piece: a set of relative (row, col)
 * offsets plus a color. Implemented as a small ADT (Abstract Data Type) —
 * its internal array is defensively copied on the way in and out so
 * nobody outside this class can mutate its representation.
 */
public class Piece {
    private final int[][] cells;   // relative offsets, e.g. {{0,0},{0,1},{1,0}}
    private final Color color;

    public Piece(int[][] cells, Color color) {
        this.cells = new int[cells.length][];
        for (int i = 0; i < cells.length; i++) {
            this.cells[i] = new int[]{cells[i][0], cells[i][1]};
        }
        this.color = color;
    }

    public int[][] getCells() {
        int[][] copy = new int[cells.length][];
        for (int i = 0; i < cells.length; i++) copy[i] = new int[]{cells[i][0], cells[i][1]};
        return copy;
    }

    public Color getColor() { return color; }

    public int getHeight() {
        int max = 0;
        for (int[] c : cells) max = Math.max(max, c[0]);
        return max + 1;
    }

    public int getWidth() {
        int max = 0;
        for (int[] c : cells) max = Math.max(max, c[1]);
        return max + 1;
    }

    /** Number of cells this piece occupies (used for scoring). */
    public int size() { return cells.length; }
}
