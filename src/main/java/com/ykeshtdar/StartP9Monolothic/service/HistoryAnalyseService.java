package com.ykeshtdar.StartP9Monolothic.service;

import com.ykeshtdar.StartP9Monolothic.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;
import java.util.stream.*;

@Service
public class HistoryAnalyseService {

    List<String> keywords = Arrays.asList("Hémoglobine A1C","Microalbumine","Taille","Poids","Fumeur","Fumeuse","Anormal","Cholestérol","Vertiges","Rechute","Réaction","Anticorps");
//    private final UserInformationRepository userInformationRepository;
//    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;
    @Value("${service.url.patientPrescriptionBase}")
    private String patientPrescriptionUrlBase;

    public HistoryAnalyseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public HistoryAnalyseService(UserInformationRepository userInformationRepository, MongoTemplate mongoTemplate) {
//        this.userInformationRepository = userInformationRepository;
//        this.mongoTemplate = mongoTemplate;
//    }

//    public UserInformation addUserInformation(UserInformation userinformation) {
//        if (isAlreadyExist(userinformation.getFirstname())){
//            throw new RuntimeException("this user is already exist");
//        }
//       return userInformationRepository.save(userinformation);
//    }

//    public UserInformation findByUsername(String name) {
//        return userInformationRepository.findByFirstname(name)
//                .orElseThrow(()->new RuntimeException("can not find user by this username"));
//    }

//    public UserInformation updateUserInformation(String firstname,
//                                                 String lastname,
//                                                 String gender,
//                                                 LocalDate birthdate,
//                                                 String address,
//                                                 String phoneNumber) {
//        if (!isAlreadyExist(firstname)){
//             throw  new RuntimeException("this user dose not exist");
//        }
//       UserInformation userInformation = findByUsername(firstname);
//        userInformation.setFirstname(firstname);
//        userInformation.setLastname(lastname);
//        userInformation.setGender(gender);
//        userInformation.setBirthdate(birthdate);//format:uuuu-MM-d
//        userInformation.setAddress(address);
//        userInformation.setPhoneNumber(phoneNumber);
//        return userInformationRepository.save(userInformation);
//    }

//    public List<UserInformation> displayAllUserInformation() {
//        return userInformationRepository.findAll();
//    }

//    public void deleteUser(String firstname) {
//        UserInformation userInformation = findByUsername(firstname);
//        userInformationRepository.delete(userInformation);
//    }


//    public Boolean isAlreadyExist(String firstname){
//       return userInformationRepository.existsByFirstname(firstname);
//    }


//    public void addPrescriptionToPatient(Integer id,Prescription prescription){
//
//        Query query = new Query(Criteria.where("id").is(id));
//        Update update = new Update().push("prescriptions",prescription);
//        mongoTemplate.updateFirst(query,update,UserInformation.class);
//
//    }

    public List<String> displayPrescriptions(Integer id) {
//      UserInformation patient = userInformationRepository.findById(id)
//              .orElseThrow(()->new RuntimeException("patient not founded"));
        System.out.println("in analyse service displayPrescription id is "+id);

        HttpHeaders headers = new HttpHeaders();
        headers.set("internalRequest","true");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String displayPrescriptionUrl = String.format("%s/prescriptions/%d",patientPrescriptionUrlBase,id);

      String url = displayPrescriptionUrl;


        Map<String,Object> uriVariable = new HashMap<>();
        uriVariable.put("id",id);

//        ResponseEntity<List<String>> response = restTemplate.exchange(
//                displayPrescriptionUrl,
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<List<String>>() {});
        ResponseEntity<List<String>> response = restTemplate.exchange(
                displayPrescriptionUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<String>>() {});

//        List<String> allTheNotes = restTemplate.getForObject(url,List.class,uriVariable);
        System.out.println("inside analyse service display Prescription and return list is "+response.getBody());
            return response.getBody();
//       return allTheNotes;
//                .stream()
//                .map(prescription -> prescription.getNote())
//              .collect(Collectors.toList());

//   return    patient.getPrescriptions()
//              .stream()
//              .map(prescription -> prescription.getNote())
//              .collect(Collectors.toList());
    }

    public Map<String, Integer> analysePatientHistory(Integer id) {
        System.out.println("in analyse service analysePatient id is "+id);
        List<String>prescriptions = displayPrescriptions(id);
        Map<String,Integer> result = new HashMap<>();

        if (prescriptions==null){
            return result;
        }

        List<String> words = wordSplitter(prescriptions);



        for (String keyword : keywords) {
            int i = 0;
            for (String word : words) {
                if (keyword.equalsIgnoreCase(word)) {
                    i++;
                }
            }
            result.put(keyword, i);
        }
        System.out.println("in analyse service analysePatient result is");
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


    public Integer calculateScore(Integer id){
        System.out.println("in analyse service calculateScore id is "+id);
       Map<String,Integer> map =  analysePatientHistory(id);

       Integer score = map.entrySet()
               .stream()
               .mapToInt(r->r.getValue())
               .sum();

//       if (score==null){
//           return 0;
//       }
        System.out.println("in analyse service calculateScore , score is "+score);
       return score;
    }

}
