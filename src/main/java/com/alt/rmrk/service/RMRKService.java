package com.alt.rmrk.service;

import com.alt.rmrk.models.*;
import com.alt.rmrk.repository.BirdLegacyRepository;
import com.alt.rmrk.repository.BirdRepository;
import com.alt.rmrk.utils.Utils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RMRKService {

    @Autowired
    BirdRepository repository;

    @Autowired
    Utils utils;

    public Nfts callRMRKApi(WebClient client, String url){
        Root response = client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Root.class)
                .block();

        Nfts responseNft = null;
        try{
            responseNft = response.getNfts().get(0);
        } catch(IndexOutOfBoundsException exception) {
            return null;
        }
        return response.getNfts().get(0);
    }

    public Nfts fetchDataFromAPI(Integer id){
        WebClient client = WebClient.create();
        Nfts birdData = callRMRKApi(client, utils.formatURL(id, URL.API));
        return birdData;
    }

    public Boolean checkPriceChangeForOneBird(Integer id){
        WebClient client = WebClient.create();
        Nfts birdDetails;

        birdDetails = callRMRKApi(client, utils.formatURL(id, URL.API));

        Optional<Bird> bird = repository.findById(id);
        Integer lastPrice = bird.get().getPrice();
        Integer currentPrice = utils.calculateFinalPrice(birdDetails.getForsale());

        if (lastPrice.equals(currentPrice)){
            return true;
        }
        return false;
    }

    public List<TelegramCard> checkPriceFullset(){
        List<TelegramCard> cards = new ArrayList<TelegramCard>();
        List<Bird> fetchedBirds = (List<Bird>) repository.findAllFullsets();

        WebClient client = WebClient.create();
        Nfts birdDetails;
        String url;
        

        for (int x=0; x < fetchedBirds.size(); x++){
            url = utils.formatURL(fetchedBirds.get(x).getBirdId(), URL.API);
            birdDetails = callRMRKApi(client,url);
            Integer newPrice = utils.calculateFinalPrice(birdDetails.getForsale());

            //Create telegram card
            if (!fetchedBirds.get(x).getPrice().equals(newPrice)) {
                TelegramCard card = new TelegramCard();
                card.setBirdName(birdDetails.getMetadata_name());
                card.setThumb(utils.formatImageSource(fetchedBirds.get(x).getBirdId()));
                card.setOldPrice(fetchedBirds.get(x).getPrice());

                if(fetchedBirds.get(x).getPrice() == 0){
                    card.setNewPrice(0);

                    System.out.println("Bird " + fetchedBirds.get(x).getName() +
                            " not for sale, link: " + utils.formatURLForDB(url));
                }else {
                    card.setNewPrice(newPrice);
                    System.out.println("Price for " +fetchedBirds.get(x).getName()
                            + " changed from "+ fetchedBirds.get(x).getPrice() + " to " + newPrice
                            + " link: " + utils.formatURLForDB(url) + "\n");
                }
                cards.add(card);
                repository.updatePrice(fetchedBirds.get(x).getBirdId(), newPrice);
            }
        }
        System.out.println("**Comparing prices finished**");
        return cards;
    }

    public TelegramCard createCard(Integer id){
        Optional<Bird> previous = repository.findById(id);
        Nfts data = fetchDataFromAPI(id);

        TelegramCard card = new TelegramCard();
        card.setBirdName(data.getMetadata_name());
        card.setOldPrice(previous.get().getPrice());
        card.setNewPrice(utils.calculateFinalPrice(data.getForsale()));
        card.setThumb(utils.formatImageSource(id));

        return card;
    }

    public List<TelegramCard> createCards(){
        List<TelegramCard> cards = new ArrayList<TelegramCard>();
        for(Integer x=2; x<= 9; x++){
            cards.add(createCard(x));
        }
        return cards;
    }

    public void populateBirdBody(Nfts birdData, Integer id){

        Integer numParts = birdData.getResources().get(0).getResources_parts().size();
        List<Resources_parts> listPart = birdData.getResources().get(0).getResources_parts();

        BirdBody birdBody = new BirdBody();
        birdBody.setBirdId(id);
        for (int i=0; i<numParts; i++){
            String part = listPart.get(i).getPart().getPart_id();
            if(part.contains("_")){
                String part_name = (part.substring(part.lastIndexOf("_")+1,part.length())).toLowerCase();
                String part_set= (part.substring(0,part.lastIndexOf("_"))).toLowerCase();

                if(part_set.equals("gem")){
                    switch (part_name){
                        /*
                        birdBody.setGemEmpty1(part_set);
                        birdBody.setGemEmpty2(part_set);
                        birdBody.setGemEmpty3(part_set);
                        birdBody.setGemEmpty4(part_set);
                        birdBody.setGemEmpty5(part_set);*/
                    }
                }else {
                    switch (part_name){
                        case "body":
                            birdBody.setBody(part_set);
                            break;
                        case "footleft":
                            birdBody.setFootLeft(part_set);
                            break;
                        case "footright":
                            birdBody.setFootRight(part_set);
                            break;
                        case "handleft":
                            birdBody.setHandLeft(part_set);
                            break;
                        case "handright":
                            birdBody.setHandRight(part_set);
                            break;
                        case "head":
                            birdBody.setHead(part_set);
                            break;
                        case "tail":
                            birdBody.setTail(part_set);
                            break;
                        case "wingleft":
                            birdBody.setWingLeft(part_set);
                            break;
                        case "wingright":
                            birdBody.setWingRight(part_set);
                            break;
                        case "beak":
                            birdBody.setBeak(part_set);
                            break;
                        case "eyes":
                            birdBody.setEyes(part_set);
                            break;
                        case "sf":
                            birdBody.setCardSf(part_set);
                            break;
                    }
                }


            } else {
                //Part does not contain a hyphen
                String part_set = part.toLowerCase();
                switch (part_set){
                    case "objectleft":
                        birdBody.setObjectLeft(part_set);
                        break;
                    case "objectright":
                        birdBody.setObjectRight(part_set);
                        break;
                    case "foreground":
                        birdBody.setForeground(part_set);
                        break;
                    case "background":
                        birdBody.setBackground(part_set);
                        break;
                }
            }
        }

    }
}
