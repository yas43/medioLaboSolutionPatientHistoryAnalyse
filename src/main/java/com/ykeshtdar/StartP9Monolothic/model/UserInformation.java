package com.ykeshtdar.StartP9Monolothic.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String firstname;
    String lastname;
    Date birthdate;
    String gender;
    String address;
    String phoneNumber;

}
