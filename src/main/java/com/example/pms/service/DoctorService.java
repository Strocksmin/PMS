package com.example.pms.service;

import com.example.pms.model.Doctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;


@Service
public class DoctorService {
    private final SessionFactory sessionFactory;
    private Session session;

    public DoctorService(SessionFactory sessionFactory) {
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

    public void addDoctor(Doctor doctor) {
        session.beginTransaction();
        session.saveOrUpdate(doctor);
        session.getTransaction().commit();
    }

    public List<Doctor> getDoctors() {
        return session.createQuery("select d from Doctor d", Doctor.class).getResultList();
    }

    public Doctor getDoctorForProposal(String specialization) {
        return session.createQuery("select d from Doctor d where d.specialization ='" + specialization + "'", Doctor.class)
                .getSingleResult();
    }

    public Doctor getDoctorByDoctorUser(String email) {
        return session.createQuery("select d from Doctor d where d.email ='" + email + "'", Doctor.class)
                .getSingleResult();
    }
}
