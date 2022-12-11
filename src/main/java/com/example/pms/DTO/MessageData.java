package com.example.pms.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MessageData implements Serializable {
    private String name;
    private String phone;
    private String text;
}
