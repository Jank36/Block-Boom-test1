package view.components;

import java.awt.Color;
import java.awt.Font;

/** Shared colours and fonts. */
public final class Theme {
    private Theme() {}

    public static final Color BG_TOP = new Color(0xC7DDFB);
    public static final Color BG_BOTTOM = new Color(0xEAF3FF);
    public static final Color PURPLE = new Color(0x5B63E6);
    public static final Color NAVY = new Color(0x2E3A9E);
    public static final Color YELLOW = new Color(0xFFC800);
    public static final Color GREEN = new Color(0x3FC14F);
    public static final Color RED = new Color(0xE53935);
    public static final Color BLUE = new Color(0x4A7FD6);
    public static final Color TEXT_DARK = new Color(0x1F2A44);
    public static final Color FIELD_TEXT = new Color(0x6F86B8);
    public static final Color TEXT_MUTED = new Color(0x6C8CD5);
    public static final Color FIELD_BORDER = new Color(0xC9D6EE);
    public static final Color BOARD_FRAME = new Color(0x9DB2DD);
    public static final Color CELL_EMPTY = new Color(0xDCE8FB);
    public static final Color CELL_LINE = new Color(0xB7C8E8);

    public static Font font(int style, int size) {
        return new Font("Tahoma", style, size);
    }
}
