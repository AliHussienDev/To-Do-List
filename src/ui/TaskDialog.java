package ui;

import model.Priority;
import model.Task;
import ui.components.ModernButton;
import ui.styles.AppColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * A custom pop-up window to Add or Edit tasks.
 */
public class TaskDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<Priority> priorityCombo;
    private boolean confirmed = false;

    public TaskDialog(Frame parent, String title, Task existingTask) {
        super(parent, title, true);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(AppColors.DARK_NAVY);
        setSize(350, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(AppColors.DARK_NAVY);
        panel.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Name Input
        JLabel nameLabel = new JLabel("Task Name:");
        nameLabel.setForeground(AppColors.MINT);
        nameLabel.setFont(AppColors.MAIN_FONT);
        
        nameField = new JTextField(existingTask != null ? existingTask.getTitle() : "");
        nameField.setBackground(new Color(15, 45, 70));
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);
        nameField.setFont(AppColors.MAIN_FONT);
        nameField.setBorder(BorderFactory.createLineBorder(AppColors.STEAL_BLUE));

        // Priority Dropdown
        JLabel priorityLabel = new JLabel("Priority Level:");
        priorityLabel.setForeground(AppColors.MINT);
        priorityLabel.setFont(AppColors.MAIN_FONT);
        
        priorityCombo = new JComboBox<>(Priority.values());
        priorityCombo.setBackground(new Color(15, 45, 70));
        priorityCombo.setForeground(Color.WHITE);
        if (existingTask != null) priorityCombo.setSelectedItem(existingTask.getPriority());

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priorityLabel);
        panel.add(priorityCombo);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(AppColors.DARK_NAVY);
        btnPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        ModernButton saveBtn = new ModernButton("Save");
        saveBtn.setPreferredSize(new Dimension(80, 35));
        saveBtn.addActionListener(e -> {
            if (!nameField.getText().trim().isEmpty()) {
                confirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a name.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        ModernButton cancelBtn = new ModernButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(80, 35));
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(cancelBtn);
        btnPanel.add(saveBtn);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // --- KEYBOARD SHORTCUTS ---
        getRootPane().setDefaultButton(saveBtn); // Enter to Save
        
        // Escape to Cancel
        getRootPane().registerKeyboardAction(e -> dispose(), 
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 
            JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public boolean isConfirmed() { return confirmed; }
    public String getTaskName() { return nameField.getText(); }
    public Priority getSelectedPriority() { return (Priority) priorityCombo.getSelectedItem(); }
}
