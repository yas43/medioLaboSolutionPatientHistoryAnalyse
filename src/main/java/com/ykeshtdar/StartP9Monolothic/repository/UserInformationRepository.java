//package com.ykeshtdar.StartP9Monolothic.repository;

import com.ykeshtdar.StartP9Monolothic.model.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;


//public interface UserInformationRepository extends MongoRepository<UserInformation,Integer> {

//   Optional<UserInformation> findByFirstname(String name);
//   Boolean existsByFirstname(String firstname);


//   @Query(value = "{ '_id': ?0 }", fields = "{'prescription' : 1}")
//   List<Prescription> findPrescriptionsByPatientId(Integer id);

//   @Query(value = "{ 'id':?0 }",update = "{ '$push':{ 'prescription':?1 }}")
//   void addPrescriptionToPatient(@Param("patientId") String patientId,
//                                 @Param("prescription") Prescription prescription);
//}
