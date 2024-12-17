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


    @GetMapping("result")
    public Map<String,Integer> analyse(@RequestParam("id")Integer id){
        return historyAnalyseService.analysePatientHistory(id);
    }

    @GetMapping("score/{id}")
    public String scoreCalculator(@PathVariable("id")Integer id, HttpServletRequest request){

        return historyAnalyseService.calculateScore(id);
    }

}
