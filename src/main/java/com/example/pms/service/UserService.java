package com.example.pms.service;

import com.example.pms.exception.UserAlreadyExistsException;
import com.example.pms.model.Role;
import com.example.pms.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final SessionFactory sessionFactory;
    private Session session;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(SessionFactory sessionFactory) {
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

    public User getUserByEmail(String email) {
        return session.createQuery("select u from User u where u.email ='" + email + "'", User.class).getSingleResult();
    }

    public List<User> getUsers() {
        return session.createQuery("select u from User u", User.class).getResultList();
    }

    public void upgradeUser(User user) {
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }

    public void addUser(User user) {
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }

    public Role getRole(String role) {
        return session.createQuery("select r from Role r where r.name ='" + role + "'", Role.class).getSingleResult();
    }
    public User getUserById(int id) {
        return session.createQuery("select u from User u where u.id ='" + id + "'", User.class).getSingleResult();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = session.createQuery("select u from User u where u.email ='" + username + "'", User.class).getSingleResult();
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public void registerUser(User user) throws UserAlreadyExistsException {
        if (checkUserExist(user.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(session.createQuery("select r from Role r where r.name ='" + "CLIENT" + "'", Role.class).getSingleResult());
        user.setRoles(roles);
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }

    private boolean checkUserExist(String email) {
        try {
            session.createQuery("select u from User u where u.email ='" + email + "'", User.class).getSingleResult();
            return true;
        } catch (NoResultException noResultException) {
            return false;
        }
    }
}
