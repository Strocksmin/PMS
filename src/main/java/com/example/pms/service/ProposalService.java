package com.example.pms.service;

import com.example.pms.model.Proposal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;


@Service
public class ProposalService {
    private final SessionFactory sessionFactory;
    private Session session;

    public ProposalService(SessionFactory sessionFactory) {
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

    public void addProposal(Proposal proposal) {
        session.beginTransaction();
        session.saveOrUpdate(proposal);
        session.getTransaction().commit();
    }

    public List<Proposal> getProposals() {
        return session.createQuery("select p from Proposal p", Proposal.class).getResultList();
    }

    public Proposal getProposal(int id) {
        return session.createQuery("select p from Proposal p where p.id ='" + id + "'", Proposal.class).getSingleResult();
    }

    public void deleteProposal(int id) {
        session.beginTransaction();
        Proposal proposal = session.load(Proposal.class, id);
        session.delete(proposal);
        session.getTransaction().commit();
    }
}
