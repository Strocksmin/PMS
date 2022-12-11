package com.example.pms.service;

import com.example.pms.model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class ClientService {
    private final SessionFactory sessionFactory;
    private Session session;

    public ClientService(SessionFactory sessionFactory) {
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

    public void addClient(Client client) {
        session.beginTransaction();
        session.saveOrUpdate(client);
        session.getTransaction().commit();
    }

    public List<Client> getClients() {
        return session.createQuery("select c from Client c order by c.datetime desc", Client.class).getResultList();
    }

    public List<Client> getClientsByEmail(String email) {
        return session.createQuery("select c from Client c where c.email ='" + email + "' order by c.datetime desc", Client.class).getResultList();
    }
}
