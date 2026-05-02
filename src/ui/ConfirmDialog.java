package ui;

import ui.components.ModernButton;
import ui.styles.AppColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * A beautiful, modern confirmation dialog.
 */
public class ConfirmDialog extends JDialog {
    private boolean confirmed = false;

    public ConfirmDialog(Frame parent, String message) {
        super(parent, "Confirm", true);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(AppColors.DARK_NAVY);
        setSize(380, 200);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        
        // Add a nice border since we have no title bar
        getRootPane().setBorder(BorderFactory.createLineBorder(AppColors.STEAL_BLUE, 1));

        // Message
        JLabel msgLabel = new JLabel("<html><div style='text-align: center; width: 300px;'>" + message + "</div></html>");
        msgLabel.setForeground(Color.WHITE);
        msgLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        msgLabel.setBorder(new EmptyBorder(30, 20, 20, 20));

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnPanel.setBackground(AppColors.DARK_NAVY);

        ModernButton cancelBtn = new ModernButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(110, 40));
        cancelBtn.addActionListener(e -> dispose());

        // Specialized Red Button for Deletion
        ModernButton deleteBtn = new ModernButton("Delete");
        deleteBtn.setPreferredSize(new Dimension(110, 40));
        deleteBtn.setBackground(new Color(255, 60, 60)); // Red
        deleteBtn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        btnPanel.add(cancelBtn);
        btnPanel.add(deleteBtn);

        add(msgLabel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // --- KEYBOARD SHORTCUTS ---
        getRootPane().setDefaultButton(deleteBtn); // Enter to Delete
        
        // Escape to Cancel
        getRootPane().registerKeyboardAction(e -> dispose(), 
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 
            JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public boolean isConfirmed() { return confirmed; }
}
