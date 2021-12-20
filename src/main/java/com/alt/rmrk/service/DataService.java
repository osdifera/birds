package com.alt.rmrk.service;

import com.alt.rmrk.models.Bird;
import com.alt.rmrk.models.BirdLegacy;
import com.alt.rmrk.models.Nfts;
import com.alt.rmrk.repository.BirdLegacyRepository;
import com.alt.rmrk.repository.BirdRepository;
import com.alt.rmrk.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class DataService {

    @Autowired
    BirdRepository repository;

    @Autowired
    BirdLegacyRepository legacyRepository;

    @Autowired
    RMRKService rmrkService;

    @Autowired
    Utils utils;

    public List<Bird> migration(){

        List<BirdLegacy> fetchedBirds = (List<BirdLegacy>) legacyRepository.findAllFullsets();

        WebClient client = WebClient.create();
        List<Bird> dailyReport= new ArrayList<Bird>();
        Nfts birdDetails;
        String url;

        for (int x=647; x < fetchedBirds.size(); x++){

            url = utils.formatURL(fetchedBirds.get(x).getId(), URL.API);
            birdDetails = rmrkService.callRMRKApi(client, url);

            Integer newPrice = utils.calculateFinalPrice(birdDetails.getForsale());

            //populate bird details
            Bird bird = new Bird();
            bird.setBirdId(fetchedBirds.get(x).getId());
            bird.setUrl(utils.formatURLForDB(url));
            //bird.setName(birdDetails.getMetadata_name());
            bird.setName(fetchedBirds.get(x).getName());
            bird.setPrice(utils.calculateFinalPrice(birdDetails.getForsale()));
            //bird.setPrice(fetchedBirds.get(x).getPrice());
            //bird.setFullset(isFullset(birdDetails));
            bird.setFullset(fetchedBirds.get(x).getFullset());
            bird.setSetName(utils.getSetName(birdDetails));
            bird.setLocalDateTime(LocalDateTime.now());
            System.out.println("*** Inserting " +fetchedBirds.get(x).getId() + "***");
            repository.save(bird);
            if (!fetchedBirds.get(x).getPrice().equals(newPrice)){
                System.out.println("*** Price for " +fetchedBirds.get(x).getId()
                        + " changed from "+ fetchedBirds.get(x).getPrice() + " to " + newPrice
                        + " link: " + url);
                //dailyReport.add(bird);
            }
        }
        System.out.println("**Finished**");
        return null;
    }

    public void insertRange(Integer start, Integer end){
        WebClient client = WebClient.create();
        Nfts birdDetails;
        String url;

        for(int i=start; i <= end; i++ ){
            url = utils.formatURL(i, URL.API);
            birdDetails = rmrkService.callRMRKApi(client, url);

            if (birdDetails == null){
                continue;
            }

            //Create new bird to save
            Bird bird = new Bird();
            bird.setBirdId(i);
            bird.setName(birdDetails.getMetadata_name());
            bird.setSetName(utils.getSetName(birdDetails));
            bird.setPrice(utils.calculateFinalPrice(birdDetails.getForsale()));
            bird.setFullset(utils.isFullset(birdDetails));
            bird.setUrl(utils.formatURLForDB(url));
            bird.setLocalDateTime(LocalDateTime.now());
            repository.save(bird);
            System.out.println("*** Bird " +i + " inserted ***");
        }
    }

    public List<Bird> getRange(Integer start, Integer end){

        List<Integer> numbers = IntStream.range(start, end+1).boxed().collect(Collectors.toList());
        List<Bird> exit = numbers.stream()
                .map(repository::findById)
                .map(Optional::get)
                .collect(Collectors.toList());

        return exit;
    }

    public List<Bird> fetchFullsets(){
        List<Bird> birds = (List<Bird>) repository.findAllFullsets();
        return birds;
    }
}
