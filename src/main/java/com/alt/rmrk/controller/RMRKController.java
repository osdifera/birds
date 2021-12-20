package com.alt.rmrk.controller;

import com.alt.rmrk.models.Bird;
import com.alt.rmrk.models.Nfts;
import com.alt.rmrk.models.TelegramCard;
import com.alt.rmrk.service.DataService;
import com.alt.rmrk.service.FileService;
import com.alt.rmrk.service.RMRKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RMRKController {

    @Autowired
    RMRKService RMRKservice;

    @Autowired
    DataService dataService;

    @Autowired
    FileService fileService;

    @GetMapping("/fetch/bird/{id}")
    public ResponseEntity<Nfts> fetchSingleBird(@PathVariable Integer id){
        Nfts response = RMRKservice.fetchDataFromAPI(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/tg/{id}")
    public ResponseEntity<TelegramCard> createTelegramCard(@PathVariable Integer id){
        TelegramCard card = RMRKservice.createCard(id);
        return ResponseEntity.ok().body(card);
    }

    @GetMapping("/tg/range")
    public ResponseEntity<List<TelegramCard>> createTelegramCards(){
        List<TelegramCard> cards = RMRKservice.createCards();
        return ResponseEntity.ok().body(cards);
    }

    @GetMapping("/insert/birds/{idStart}/{idEnd}")
    public ResponseEntity<String> insertRange(@PathVariable Integer idStart, @PathVariable Integer idEnd){
        dataService.insertRange(idStart,idEnd);
        return ResponseEntity.ok().body("Fetched range");
    }

    @GetMapping("/check/price/{id}")
    public ResponseEntity<Boolean> checkPriceForSingleBird(@PathVariable Integer id){
        Boolean response = RMRKservice.checkPriceChangeForOneBird(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/check/birds/{idStart}/{idEnd}")
    public ResponseEntity<List<Bird>> checkPriceRange(@PathVariable Integer idStart, @PathVariable Integer idEnd){
        List<Bird> response = dataService.getRange(idStart,idEnd);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/check/price/fullset")
    public ResponseEntity<List<TelegramCard>> price(){
        List<TelegramCard> response = RMRKservice.checkPriceFullset();
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/generate/report/fullset")
    public ResponseEntity<Boolean> generateFullsetReport(){
        Boolean response = fileService.generateCSVReport();
        return ResponseEntity.ok().body(response);
    }

}
