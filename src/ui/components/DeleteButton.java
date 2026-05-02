package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A modern, circular red delete button that draws its own "X".
 */
public class DeleteButton extends JButton {
    private boolean isHovered = false;

    public DeleteButton() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(35, 35));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background circle on hover
        if (isHovered) {
            g2.setColor(new Color(255, 60, 60));
            g2.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
            g2.setColor(Color.WHITE); // X becomes white
        } else {
            g2.setColor(new Color(255, 80, 80)); // X stays red
        }

        // Draw the X manually using lines
        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int p = 11; // padding
        g2.drawLine(p, p, getWidth() - p, getHeight() - p);
        g2.drawLine(getWidth() - p, p, p, getHeight() - p);

        g2.dispose();
    }
}
