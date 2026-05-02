package ui.components;

import ui.styles.AppColors;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * A custom UI for a sleek, modern scrollbar.
 */
public class ModernScrollBarUI extends BasicScrollBarUI {
    
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = AppColors.STEAL_BLUE;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // Track is invisible
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw rounded thumb (thicker with less padding)
        g2.setColor(thumbColor);
        int x = thumbBounds.x + 2;
        int y = thumbBounds.y + 2;
        int width = thumbBounds.width - 4;
        int height = thumbBounds.height - 4;
        
        g2.fillRoundRect(x, y, width, height, 8, 8);
        g2.dispose();
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(0, 40); // Ensures thumb is always visible
    }
}
