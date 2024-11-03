package com.ykeshtdar.StartP9Monolothic.service;

import com.ykeshtdar.StartP9Monolothic.model.*;
import com.ykeshtdar.StartP9Monolothic.repository.*;
import org.bson.types.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

@Service
public class UserInformationService {

    List<String> keywords = Arrays.asList("Hémoglobine A1C","Microalbumine","Taille","Poids","Fumeur","Fumeuse","Anormal","Cholestérol","Vertiges","Rechute","Réaction","Anticorps");
    private final UserInformationRepository userInformationRepository;
    private final MongoTemplate mongoTemplate;

    public UserInformationService(UserInformationRepository userInformationRepository, MongoTemplate mongoTemplate) {
        this.userInformationRepository = userInformationRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public UserInformation addUserInformation(UserInformation userinformation) {
        if (isAlreadyExist(userinformation.getFirstname())){
            throw new RuntimeException("this user is already exist");
        }
       return userInformationRepository.save(userinformation);
    }

    public UserInformation findByUsername(String name) {
        return userInformationRepository.findByFirstname(name)
                .orElseThrow(()->new RuntimeException("can not find user by this username"));
    }

    public UserInformation updateUserInformation(String firstname,
                                                 String lastname,
                                                 String gender,
                                                 LocalDate birthdate,
                                                 String address,
                                                 String phoneNumber) {
        if (!isAlreadyExist(firstname)){
             throw  new RuntimeException("this user dose not exist");
        }
       UserInformation userInformation = findByUsername(firstname);
        userInformation.setFirstname(firstname);
        userInformation.setLastname(lastname);
        userInformation.setGender(gender);
        userInformation.setBirthdate(birthdate);//format:uuuu-MM-d
        userInformation.setAddress(address);
        userInformation.setPhoneNumber(phoneNumber);
        return userInformationRepository.save(userInformation);
    }

    public List<UserInformation> displayAllUserInformation() {
        return userInformationRepository.findAll();
    }

    public void deleteUser(String firstname) {
        UserInformation userInformation = findByUsername(firstname);
        userInformationRepository.delete(userInformation);
    }


    public Boolean isAlreadyExist(String firstname){
       return userInformationRepository.existsByFirstname(firstname);
    }


    public void addPrescriptionToPatient(Integer id,Prescription prescription){

        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().push("prescriptions",prescription);
        mongoTemplate.updateFirst(query,update,UserInformation.class);

    }

    public List<String> displayPrescriptions(Integer id) {
      UserInformation patient = userInformationRepository.findById(id)
              .orElseThrow(()->new RuntimeException("patient not founded"));


   return    patient.getPrescriptions()
              .stream()
              .map(prescription -> prescription.getNote())
              .collect(Collectors.toList());
    }

    public Map<String, Integer> analysePatientHistory(Integer id) {
        List<String>prescriptions = displayPrescriptions(id);
        Map<String,Integer> result = new HashMap<>();

        List<String> words = wordSplitter(prescriptions);

        for (String keyword : keywords) {
            int i = 0;
            for (String word : words) {
                if (keyword.equals(word)) {
                    i++;
                }
            }
            result.put(keyword, i);
        }

        return result;
    }


    public List<String> wordSplitter(List<String> list){
        List<String> wordOfAllPrescription = new LinkedList<>();
        for (String prescription:list){
            String[] words = prescription.split(" ");
            for (String word:words){
                wordOfAllPrescription.add(word);
            }
        }

        return wordOfAllPrescription;
    }

}
