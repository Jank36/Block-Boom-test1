package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * PieceGenerator.java
 *
 * Factory responsible for creating random Pieces from a fixed shape
 * library (dots, lines, squares, L-shapes, S/Z-shapes, T-shapes...).
 * Keeping the shape data and randomization here — instead of inside
 * Board or GamePanel — follows the Single Responsibility Principle:
 * this class's only job is "produce a piece".
 */
public class PieceGenerator {

    // Each shape is a list of {row, col} offsets, top-left origin.
    private static final int[][][] SHAPES = {
        {{0,0}},                                                 // 1x1 dot
        {{0,0},{0,1}},                                           // 1x2 horizontal
        {{0,0},{1,0}},                                           // 2x1 vertical
        {{0,0},{0,1},{0,2}},                                     // 1x3 horizontal
        {{0,0},{1,0},{2,0}},                                     // 3x1 vertical
        {{0,0},{0,1},{0,2},{0,3}},                               // 1x4 horizontal
        {{0,0},{1,0},{2,0},{3,0}},                               // 4x1 vertical
        {{0,0},{0,1},{0,2},{0,3},{0,4}},                         // 1x5 horizontal
        {{0,0},{1,0},{2,0},{3,0},{4,0}},                         // 5x1 vertical
        {{0,0},{0,1},{1,0},{1,1}},                               // 2x2 square
        {{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}}, // 3x3 square
        {{0,0},{0,1},{1,0}},                                     // small L (4 rotations)
        {{0,0},{0,1},{1,1}},
        {{0,1},{1,0},{1,1}},
        {{0,0},{1,0},{1,1}},
        {{0,0},{1,0},{2,0},{2,1}},                               // long L (4 rotations)
        {{0,0},{0,1},{1,0},{2,0}},
        {{0,1},{1,1},{2,0},{2,1}},
        {{0,0},{1,0},{1,1},{2,1}},                               // S / Z shapes
        {{0,1},{1,0},{1,1},{2,0}},
        {{0,0},{0,1},{0,2},{1,1}},                               // T shape
        {{0,1},{1,0},{1,1},{1,2}},
    };

    private static final Color[] COLORS = {
        new Color(231, 76, 60),
        new Color(52, 152, 219),
        new Color(46, 204, 113),
        new Color(241, 196, 15),
        new Color(155, 89, 182),
        new Color(230, 126, 34),
        new Color(26, 188, 156),
    };

    private final Random random = new Random();

    public Piece generateRandom() {
        int[][] shape = SHAPES[random.nextInt(SHAPES.length)];
        Color color = COLORS[random.nextInt(COLORS.length)];
        return new Piece(shape, color);
    }

    public List<Piece> generateTray(int count) {
        List<Piece> tray = new ArrayList<>();
        for (int i = 0; i < count; i++) tray.add(generateRandom());
        return tray;
    }
}
