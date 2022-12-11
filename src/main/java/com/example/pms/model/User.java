package com.example.pms.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не может быть пустым.")
    @Column(name = "user_name")
    private String name;
    @NotEmpty(message = "Специальность не может быть пустой.")
    @Column(name = "specialization")
    private String specialization;
    @NotEmpty(message = "Фамилия не может быть пустой.")
    @Column(name = "user_lastname")
    private String lastname;
    @NotEmpty(message = "Электронная почта не может быть пустой.")
    @Column(name = "user_email")
    private String email;
    @NotEmpty(message = "Пароль не может быть пустым.")
    @Column(name = "user_password")
    private String password;
    @Pattern(regexp = "\\+[0-9]{11}",
            message = "Формат поля телефона: +78005553535")
    @NotEmpty(message = "Телефон не может быть пустым.")
    @Column(name = "user_number")
    private String number;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "users_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private Collection<Task> tasks;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Task> assignedTasks;

    public String rolesToString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Роли: ");
        for (Role role: roles) {
            stringBuffer.append(role.getName() + ", ");
        }
        stringBuffer.delete(stringBuffer.length() - 2,  stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNumber() {
        return number;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public List<Task> getNotFinishedTasks() {
        List<Task> notFinishedTasks = new ArrayList<>();
        for (Task t : getTasks()) {
            if (!t.isFinished()) notFinishedTasks.add(t);
        }
        return notFinishedTasks;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
