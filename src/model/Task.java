package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents a single task in our list.
 * It implements Serializable so we can save it to a file.
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private boolean isCompleted;

    public Task(String title, String description, Priority priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}
