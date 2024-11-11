package com.example.demo;

import java.io.*;
import java.util.List;

import entity.TasksEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(
        name = "TaskServletServlet",
        urlPatterns = {"/task"}
)
public class TaskServlet extends HttpServlet {
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch(action) {
            case "list":
                listTasks(request, response);
                break;
            case "add":
                request.getRequestDispatcher("addTask.jsp").forward(request, response);
                break;
            case "delete":
                request.getRequestDispatcher("deleteTask.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }

    private void listTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<TasksEntity> tasks = em.createNamedQuery("TasksEntity.findAll", TasksEntity.class).getResultList();
        em.close();

        // Debugging: Print retrieved tasks to console
        for (TasksEntity task : tasks) {
            System.out.println("Task ID: " + task.getId() + ", Description: " + task.getDescription());
        }

        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("listTasks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch(action) {
            case "add":
                addTask(request, response);
                break;
            case "delete":
                deleteTask(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }

    private void addTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TasksEntity task = new TasksEntity();
            task.setDescription(description);
            em.persist(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        response.sendRedirect("task?action=list");
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            TasksEntity task = em.find(TasksEntity.class, id);
            if(task != null) {
                em.remove(task);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        response.sendRedirect("task?action=list");
    }

//    // SQL Injection code for DEMONSTRATION PURPOSES ONLY!
//    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String id = request.getParameter("id");
//        EntityManager em = entityManagerFactory.createEntityManager();
//        EntityTransaction transaction = em.getTransaction();
//
//        try {
//            transaction.begin();
//
//            // Vulnerable to SQL injection by directly concatenating `id` into the query
//            System.out.println("Executing Query: DELETE FROM tasks WHERE id = " + id);
//            em.createNativeQuery("DELETE FROM tasks").executeUpdate();
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting task.");
//            return;
//        } finally {
//            em.close();
//        }
//        response.sendRedirect("task?action=list");
//    }

    public void destroy() {
    }
}