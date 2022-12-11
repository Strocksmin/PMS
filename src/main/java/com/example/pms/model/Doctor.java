package com.example.pms.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor {
    @Id
    @SequenceGenerator(name = "doctors_seq", sequenceName =
            "doctors_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "doctors_seq", strategy =
            GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;
    @Column(name = "doctor_name")
    private String name;
    @Column(name = "doctor_lastname")
    private String lastname;
    @Column(name = "doctor_email")
    private String email;
    @Column(name = "doctor_number")
    private String number;
    @Column(name = "doctor_specialization")
    private String specialization;

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "doctor")
    private List<Client> clients;
}
