package ui.components;

import model.Task;
import ui.styles.AppColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * This represents a single task "card" in the list.
 */
public class TaskItemPanel extends JPanel {
    public TaskItemPanel(Task task, Runnable onDelete, Runnable onToggle, Runnable onEdit) {
        setLayout(new BorderLayout(15, 0));
        setBackground(AppColors.DARK_NAVY);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.STEAL_BLUE, 1),
            new EmptyBorder(10, 15, 10, 15)
        ));

        // Checkbox for completion
        ModernCheckBox doneBox = new ModernCheckBox();
        doneBox.setSelected(task.isCompleted());
        doneBox.addActionListener(e -> {
            task.setCompleted(doneBox.isSelected());
            onToggle.run();
        });

        // Task Details
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(AppColors.TITLE_FONT);
        titleLabel.setForeground(task.isCompleted() ? AppColors.TEXT_GRAY : AppColors.MINT);

        JLabel infoLabel = new JLabel(task.getPriority() + " Priority - Due: " + task.getDueDate());
        infoLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
        infoLabel.setForeground(AppColors.TEXT_GRAY);

        centerPanel.add(titleLabel);
        centerPanel.add(infoLabel);

        // Actions Panel (Edit and Delete)
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        actionsPanel.setOpaque(false);

        JButton editBtn = new ModernButton("Edit");
        editBtn.setPreferredSize(new Dimension(60, 30));
        editBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        editBtn.addActionListener(e -> onEdit.run());

        DeleteButton deleteBtn = new DeleteButton();
        deleteBtn.addActionListener(e -> onDelete.run());

        actionsPanel.add(editBtn);
        actionsPanel.add(deleteBtn);

        add(doneBox, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.EAST);
        
        // Size constraint
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
    }
}
