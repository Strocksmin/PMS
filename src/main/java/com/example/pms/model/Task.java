package com.example.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Имя не может быть пустым.")
    @Column(name = "description")
    private String description;
    @NotEmpty(message = "Фамилия не может быть пустой.")
    @Column(name = "creation_date")
    private String creation_date;
    @Column(name = "finish_date")
    private String finish_date;
    @Column(name = "user_id")
    private int userID;
    @Column(name = "finished")
    private boolean finished;

    @ManyToMany(mappedBy = "tasks")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getFinish_date() {
        return finish_date;
    }

    public User getAppointee() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getUsers() {
        return users;
    }

    public User getUser() {
        return user;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
