package com.ykeshtdar.StartP9Monolothic.model;



import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDate;


@Document("loginForm")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation {
   @Id
    Integer id;
    String firstname;
    String lastname;
    LocalDate birthdate;
    String gender;
    String address;
    String phoneNumber;

}
