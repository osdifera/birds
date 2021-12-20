package com.alt.rmrk.service;

import com.alt.rmrk.models.Bird;
import com.alt.rmrk.repository.BirdRepository;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    BirdRepository repository;

    private void generateFile(List<Bird> birds) throws IOException {
        File csvOutputFile = new File("fullset.csv");
        CsvMapper mapper = new CsvMapper();
        mapper.findAndRegisterModules();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

        CsvSchema schema = CsvSchema.builder().setUseHeader(true)
                .addColumn("birdName")
                .addColumn("birdPrice")
                .addColumn("birdUrl")
                .addColumn("birdSet")
                .build();
        ObjectWriter writer = mapper.writerFor(Bird.class).with(schema);
        writer.writeValues(csvOutputFile).writeAll(birds);
        System.out.println("****Birds saved to CSV*****");
        System.out.println(csvOutputFile);
    }

    public Boolean generateCSVReport(){
        List<Bird> birds = (List<Bird>) repository.findAllFullsets();
        try {
            generateFile(birds);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
