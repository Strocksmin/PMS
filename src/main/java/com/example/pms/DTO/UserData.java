package com.example.pms.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserData implements Serializable {
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String password;
}
