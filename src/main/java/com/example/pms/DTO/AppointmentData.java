package com.example.pms.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentData {

    private String name;

    private String lastname;

    private String email;

    private String number;

    private String specialization;

    private String datetime;

    public AppointmentData() {
    }
}
