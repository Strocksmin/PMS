package com.example.pms.service;

import com.example.pms.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class RoleService {
    private final SessionFactory sessionFactory;
    private Session session;

    public RoleService(SessionFactory sessionFactory) {
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

    public List<Role> getRoles() {
        return session.createQuery("select r from Role r", Role.class).getResultList();
    }
}
