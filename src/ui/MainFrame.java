package ui;

import model.Priority;
import model.Task;
import service.TaskManager;
import ui.components.ModernButton;
import ui.components.ModernScrollBarUI;
import ui.components.TaskItemPanel;
import ui.styles.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

/**
 * The main UI frame for the To-Do application.
 */
public class MainFrame extends JFrame {
    private TaskManager taskManager;
    private JPanel taskListContainer;
    private JTextField searchField;

    public MainFrame() {
        this.taskManager = new TaskManager();
        setupWindow();
        initializeUI();
        refreshTaskList("");
    }

    private void setupWindow() {
        setTitle("DoList - Modern Task Manager");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(AppColors.DARK_NAVY);

        // Set window icon
        try {
            ImageIcon icon = new ImageIcon("Logo.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Logo not found for window icon.");
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // --- Header Section ---
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(AppColors.DARK_NAVY);
        header.setBorder(new EmptyBorder(25, 20, 15, 20));

        // Create a horizontal row with 0 gaps for perfect alignment
        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleRow.setOpaque(false);
        titleRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add Logo
        try {
            ImageIcon logoIcon = new ImageIcon("Logo.png");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
            titleRow.add(logoLabel);
            
            // Add space between logo and title manually
            titleRow.add(Box.createRigidArea(new Dimension(15, 0)));
        } catch (Exception e) {
            // Skip if logo is missing
        }

        JLabel appTitle = new JLabel("My Tasks");
        appTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        appTitle.setForeground(AppColors.MINT);
        titleRow.add(appTitle);

        header.add(titleRow);
        header.add(Box.createRigidArea(new Dimension(0, 20)));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 40));
        searchField.setBackground(new Color(15, 45, 70));
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchField.setFont(AppColors.MAIN_FONT);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.STEAL_BLUE, 1),
            new EmptyBorder(0, 10, 0, 10)
        ));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                refreshTaskList(searchField.getText());
            }
        });
        searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(searchField);

        add(header, BorderLayout.NORTH);

        // --- Task List Section ---
        taskListContainer = new JPanel();
        taskListContainer.setLayout(new BoxLayout(taskListContainer, BoxLayout.Y_AXIS));
        taskListContainer.setBackground(AppColors.DARK_NAVY);
        taskListContainer.setBorder(new EmptyBorder(0, 20, 0, 20));
        
        JScrollPane scrollPane = new JScrollPane(taskListContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(AppColors.DARK_NAVY);
        
        // Apply Modern ScrollBar UI
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(14, 0));
        scrollPane.getVerticalScrollBar().setOpaque(true);
        scrollPane.getVerticalScrollBar().setBackground(AppColors.DARK_NAVY);
        scrollPane.getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        add(scrollPane, BorderLayout.CENTER);

        // --- Footer Section ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(AppColors.DARK_NAVY);
        footer.setBorder(new EmptyBorder(15, 20, 25, 20));

        ModernButton addBtn = new ModernButton("+ Add Task");
        addBtn.setPreferredSize(new Dimension(140, 45));
        addBtn.addActionListener(e -> showAddTaskDialog());
        footer.add(addBtn);

        add(footer, BorderLayout.SOUTH);
    }

    private void refreshTaskList(String query) {
        taskListContainer.removeAll();
        for (Task task : taskManager.getTasks()) {
            if (task.getTitle().toLowerCase().contains(query.toLowerCase())) {
                TaskItemPanel item = new TaskItemPanel(
                    task, 
                    () -> delete_logic(task), 
                    () -> toggle_logic(), 
                    () -> edit_logic(task)
                );
                taskListContainer.add(item);
                taskListContainer.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        taskListContainer.revalidate();
        taskListContainer.repaint();
    }

    private void showAddTaskDialog() {
        TaskDialog dialog = new TaskDialog(this, "Create New Task", null);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            Task newTask = new Task(
                dialog.getTaskName(), 
                "", 
                dialog.getSelectedPriority(), 
                LocalDate.now().plusDays(1)
            );
            taskManager.addTask(newTask);
            refreshTaskList(searchField.getText());
        }
    }

    private void edit_logic(Task task) {
        TaskDialog dialog = new TaskDialog(this, "Edit Task", task);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            task.setTitle(dialog.getTaskName());
            task.setPriority(dialog.getSelectedPriority());
            taskManager.saveTasks();
            refreshTaskList(searchField.getText());
        }
    }

    private void delete_logic(Task task) {
        ConfirmDialog dialog = new ConfirmDialog(this, "Are you sure you want to delete<br><b>" + task.getTitle() + "</b>?");
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            taskManager.removeTask(task);
            refreshTaskList(searchField.getText());
        }
    }

    private void toggle_logic() {
        taskManager.saveTasks();
        refreshTaskList(searchField.getText());
    }
}
