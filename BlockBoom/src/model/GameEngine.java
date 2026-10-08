package model;

import java.util.List;

/**
 * GameEngine.java
 *
 * Model class that holds the state of ONE round: the board, the tray of
 * three pieces, the score and the game-over flag. It applies the scoring
 * rules (cells placed + bonus for line clears) using Board / Piece /
 * PieceGenerator. Knows nothing about drawing, mouse input or files, so
 * GamePanel only has to translate mouse events into engine calls.
 */
public class GameEngine {
    public static final int TRAY_SIZE = 3;

    private final Board board = new Board();
    private final PieceGenerator generator = new PieceGenerator();
    private final Piece[] tray = new Piece[TRAY_SIZE];
    private int score;
    private boolean gameOver;

    public GameEngine() {
        newGame();
    }

    public void newGame() {
        board.reset();
        score = 0;
        gameOver = false;
        refillTray();
    }

    public Board getBoard() { return board; }
    public Piece getPiece(int slot) { return tray[slot]; }
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }

    /** True if the piece in the given tray slot can legally go at (row, col). */
    public boolean canPlace(int slot, int row, int col) {
        return !gameOver && tray[slot] != null && board.canPlace(tray[slot], row, col);
    }

    /** Places the tray piece if legal. Returns true when the move was made. */
    public boolean tryPlace(int slot, int row, int col) {
        if (!canPlace(slot, row, col)) return false;
        Piece p = tray[slot];
        board.place(p, row, col);
        score += p.size();
        tray[slot] = null;

        int cleared = board.clearFullLines();
        if (cleared > 0) score += cleared * 10 * cleared; // bigger bonus for multi-line clears

        if (trayEmpty()) refillTray();
        checkGameOver();
        return true;
    }

    private void refillTray() {
        List<Piece> fresh = generator.generateTray(TRAY_SIZE);
        for (int i = 0; i < TRAY_SIZE; i++) tray[i] = fresh.get(i);
    }

    private boolean trayEmpty() {
        for (Piece p : tray) if (p != null) return false;
        return true;
    }

    private void checkGameOver() {
        for (Piece p : tray) {
            if (p != null && !board.hasNoValidMove(p)) return; // at least one piece is placeable
        }
        gameOver = true;
    }
}
