package com.ykeshtdar.StartP9Monolothic.controller;

import com.ykeshtdar.StartP9Monolothic.service.*;
import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("analyse")
public class HistoryAnalyseController {
    private final HistoryAnalyseService historyAnalyseService;

    public HistoryAnalyseController(HistoryAnalyseService historyAnalyseService) {
        this.historyAnalyseService = historyAnalyseService;
    }

//    @GetMapping
//    public UserInformation displayUserByName(@RequestParam("firstname")String name){
//        return userInformationService.findByUsername(name);
//    }

//    @GetMapping("/all")
//    public List<UserInformation> displayAllUser(){
//        return userInformationService.displayAllUserInformation();
//    }

//    @PostMapping
//    public UserInformation addUser(@RequestBody UserInformation userInformation){
//         return userInformationService.addUserInformation(userInformation);
//    }

//    @PutMapping("/update")
//    public UserInformation update(@RequestParam("firstname")String firstname,
//                                  @RequestParam("lastname")String lastname,
//                                  @RequestParam("gender")String gender,
//                                  @RequestParam("birthdate") LocalDate birthdate,
//                                  @RequestParam("address")String address,
//                                  @RequestParam("phoneNumber")String phoneNumber
//                                  ){
//
//        return userInformationService.updateUserInformation(firstname,lastname,gender,birthdate,address,phoneNumber);
//    }

//    @DeleteMapping
//    public void deleteUser(@RequestParam("firstname")String firstname){
//         userInformationService.deleteUser(firstname);
//    }



//    @PostMapping("addPrescription")
//    public void addPrescription(@RequestParam("id")Integer id,@RequestBody Prescription prescription){
//         userInformationService.addPrescriptionToPatient(id,prescription);
//    }


//    @GetMapping("prescriptions")
//    public List<String> displayPrescriptions(@RequestParam("id")Integer id){
//        return userInformationService.displayPrescriptions(id);
//    }

    @GetMapping("result")
    public Map<String,Integer> analyse(@RequestParam("id")Integer id){
        return historyAnalyseService.analysePatientHistory(id);
    }

    @GetMapping("score/{id}")
    public Integer scoreCalculator(@PathVariable("id")Integer id, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principle = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principle;
        System.out.println("inside analyse and received token in request.getheaders.auth.7 is "+request.getHeader(HttpHeaders.AUTHORIZATION.substring(7)));
        System.out.println("inside analyse and username is "+userDetails.getUsername());
        System.out.println("inside analyse and password is "+userDetails.getPassword());
        System.out.println("inside analyse controller score method score is "+historyAnalyseService.calculateScore(id));
        return historyAnalyseService.calculateScore(id);
    }

}
