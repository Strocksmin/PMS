package com.example.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {
    @Id
    @SequenceGenerator(name = "clients_seq", sequenceName =
            "clients_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "clients_seq", strategy =
            GenerationType.SEQUENCE)
    private int id;
    @Column(name = "client_name")
    private String name;
    @Column(name = "client_lastname")
    private String lastname;
    @Column(name = "client_email")
    private String email;
    @Column(name = "client_number")
    private String number;
    @Column(name = "doctor_id")
    private int doctorID;
    @Column(name = "client_datetime")
    private String datetime;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id", insertable = false, updatable = false)
    private Doctor doctor;

    public Client(String name, String lastname, String email, String number, int doctorID, String datetime) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.number = number;
        this.doctorID = doctorID;
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", doctorID=" + doctorID +
                ", datetime='" + datetime + '\'' +
                ", doctor=" + doctor +
                '}';
    }

    public Client() {

    }

    public Doctor getDoctor() {
        return doctor;
    }
}
