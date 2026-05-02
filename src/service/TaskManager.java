package service;

import model.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of tasks and handles file saving/loading.
 */
public class TaskManager {
    private static final String FILE_NAME = "tasks.dat";
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = loadTasks();
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        saveTasks();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Saves the list of tasks to a local file.
     */
    public void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the local file.
     */
    @SuppressWarnings("unchecked")
    private List<Task> loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
