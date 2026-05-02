package ui.components;

import ui.styles.AppColors;
import javax.swing.*;
import java.awt.*;

/**
 * A custom-painted modern checkbox.
 */
public class ModernCheckBox extends JToggleButton {
    public ModernCheckBox() {
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(28, 28));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = 22;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        if (isSelected()) {
            // Background when checked (Mint)
            g2.setColor(AppColors.MINT);
            g2.fillRoundRect(x, y, size, size, 8, 8);

            // Draw Checkmark
            g2.setColor(AppColors.DARK_NAVY);
            g2.setStroke(new BasicStroke(2.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            // A precise "V" shape
            g2.drawLine(x + 6, y + 11, x + 10, y + 15);
            g2.drawLine(x + 10, y + 15, x + 16, y + 7);
        } else {
            // Background when unchecked (Darker)
            g2.setColor(new Color(15, 45, 70));
            g2.fillRoundRect(x, y, size, size, 8, 8);
            
            // Outline when unchecked
            g2.setColor(AppColors.STEAL_BLUE);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(x, y, size, size, 8, 8);
        }

        g2.dispose();
    }
}
