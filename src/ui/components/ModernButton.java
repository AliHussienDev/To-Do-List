package ui.components;

import ui.styles.AppColors;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A custom button that looks modern and reacts to the mouse.
 */
public class ModernButton extends JButton {
    private Color hoverColor;
    private Color normalColor;

    public ModernButton(String text) {
        super(text);
        this.normalColor = AppColors.STEAL_BLUE;
        this.hoverColor = AppColors.LIGHT_TEAL;

        setBackground(normalColor);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(AppColors.MAIN_FONT);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { setBackground(hoverColor); repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { setBackground(normalColor); repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Background color
        g2.setColor(getBackground() != null ? getBackground() : normalColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        
        super.paintComponent(g);
        g2.dispose();
    }
}
