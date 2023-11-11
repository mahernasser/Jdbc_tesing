package com.maher.dao;

import lombok.*;


import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor

public class Employee {
    int id;
    String name;
    double salary;
    boolean gender;
    Date birthDate;

}
