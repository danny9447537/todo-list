import entity.TasksEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class ToDoListCLI {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ToDoListCLI() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Danny's To-Do List (Command Line Version)");
        System.out.println("1. Enter 'add' a task.");
        System.out.println("2. Enter 'delete' to delete a task.");
        System.out.println("3. Enter 'list' to show task list.");
        System.out.println("4. Enter 'exit' to quit.");

        while (true) {
            System.out.println("Command: ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add":
                    System.out.println("Enter task: ");
                    String taskDescription = scanner.nextLine();
                    addTask(taskDescription);
                    break;
                case "delete":
                    System.out.println("Enter task number to delete: ");
                    int taskID = scanner.nextInt();
                    scanner.nextLine();
                    deleteTask(taskID);
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    System.out.println("App is exiting...");
                    closeEntityManager();
                    return;
                default:
                    System.out.println("Invalid command. Try again.");
                    break;
            }
        }
    }

    private void addTask(String taskDescription) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TasksEntity task = new TasksEntity();
            task.setDescription(taskDescription);
            entityManager.persist(task);
            transaction.commit();
            System.out.println("Task added successfully with ID: " + task.getId());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private void deleteTask(int index) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TasksEntity task = entityManager.find(TasksEntity.class, index);
            if (task != null) {
                entityManager.remove(task);
                transaction.commit();
                System.out.println("Task removed: " + task.getDescription());
            } else {
                System.out.println("Task not found." + index + " not found.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private void listTasks() {
        List<TasksEntity> tasks =
                entityManager.createNamedQuery("TasksEntity.findAll", TasksEntity.class).getResultList();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (TasksEntity task : tasks) {
                System.out.println("Task ID: " + task.getId() + " Description: " + task.getDescription());
            }
        }
    }

    private void closeEntityManager() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManager.close();
        }
    }
}
