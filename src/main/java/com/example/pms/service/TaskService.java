package com.example.pms.service;

import com.example.pms.model.Client;
import com.example.pms.model.Proposal;
import com.example.pms.model.Task;
import com.example.pms.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class TaskService {
    private final SessionFactory sessionFactory;
    private Session session;

    public TaskService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    @PreDestroy
    public void unSession() {
        session.close();
    }

    public void addTask(Task task) {
        session.beginTransaction();
        session.saveOrUpdate(task);
        session.getTransaction().commit();
    }

    public List<Task> getTasks() {
        return session.createQuery("select t from Task t", Task.class).getResultList();
    }

    public Task getTask(int id) {
        return session.createQuery("select t from Task t where t.id ='" + id + "'", Task.class).getSingleResult();
    }

    public List<Task> getNotFinishedTasks() {
        return session.createQuery("select t from Task t where t.finished = false", Task.class).getResultList();
    }

    public List<Task> getNotFinishedTasksForUser(int id) {
        return session.createQuery("select t from Task t where t.finished = false and t.userID = '" + id + "'", Task.class).getResultList();
    }

    public void deleteTask(int id) {
        session.beginTransaction();
        Task task = session.load(Task.class, id);
        session.delete(task);
        session.getTransaction().commit();
    }

}
