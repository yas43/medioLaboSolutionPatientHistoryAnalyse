package com.ykeshtdar.StartP9Monolothic.repository;

import com.ykeshtdar.StartP9Monolothic.model.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;


public interface UserInformationRepository extends MongoRepository<UserInformation,Integer> {

   Optional<UserInformation> findByFirstname(String name);
   Boolean existsByFirstname(String firstname);
}
