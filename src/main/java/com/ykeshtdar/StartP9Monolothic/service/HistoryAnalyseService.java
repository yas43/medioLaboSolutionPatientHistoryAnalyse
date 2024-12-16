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

    private final RestTemplate restTemplate;
    @Value("${service.url.patientPrescriptionBase}")
    private String patientPrescriptionUrlBase;

    public HistoryAnalyseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public List<String> displayPrescriptions(Integer id) {
//      UserInformation patient = userInformationRepository.findById(id)
//              .orElseThrow(()->new RuntimeException("patient not founded"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("internalRequest","true");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String displayPrescriptionUrl = String.format("%s/prescriptions/%d",patientPrescriptionUrlBase,id);


        Map<String,Object> uriVariable = new HashMap<>();
        uriVariable.put("id",id);


        ResponseEntity<List<String>> response = restTemplate.exchange(
                displayPrescriptionUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<String>>() {});
            return response.getBody();

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

        System.out.println("in analyse service calculateScore , score is "+score);
       return score;
    }

}
