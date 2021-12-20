package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="birds")
public class Bird {
    @Id
    private Integer birdId;
    @JsonProperty("birdUrl")
    private String url;
    @JsonProperty("birdName")
    private String name;
    @JsonProperty("birdPrice")
    private Integer price;
    private Integer fullset;
    @JsonProperty("birdSet")
    private String setName;
    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;
}
