package com.ykeshtdar.StartP9Monolothic.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Document("prescription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id
    Integer id;
//    String firstname;
    String note;
}
