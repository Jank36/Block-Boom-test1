package view.components;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class LogoLabel extends JComponent {
    private static final int W = 395;
    private static final int H = 222;

    private final BufferedImage logo = loadLogo();

    public LogoLabel() {
        setPreferredSize(new Dimension(W, H));
        setMaximumSize(new Dimension(W, H));
        setAlignmentX(CENTER_ALIGNMENT);
    }

    private static BufferedImage loadLogo() {
    try (InputStream in = LogoLabel.class.getResourceAsStream("/img/img.png")) {
        if (in == null) {
            System.err.println("หาไฟล์ /img/img.png ไม่เจอ");
            return null;
        }
        return ImageIO.read(in);
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}


    @Override
    protected void paintComponent(Graphics g) {
        if (logo == null) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // ย่อรูปให้พอดีกรอบ โดยรักษาสัดส่วนเดิม และจัดกึ่งกลาง
        double scale = Math.min((double) getWidth() / logo.getWidth(),
                                (double) getHeight() / logo.getHeight());
        int w = (int) (logo.getWidth() * scale);
        int h = (int) (logo.getHeight() * scale);
        int x = (getWidth() - w) / 2;
        int y = (getHeight() - h) / 2;
        g2.drawImage(logo, x, y, w, h, null);
        g2.dispose();
    }
}
