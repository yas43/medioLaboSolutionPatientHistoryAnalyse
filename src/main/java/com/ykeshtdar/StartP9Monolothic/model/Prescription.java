package com.ykeshtdar.StartP9Monolothic.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    Integer id;
    String note;
}
