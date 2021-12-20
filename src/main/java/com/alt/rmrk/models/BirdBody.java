package com.alt.rmrk.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="body_parts")
public class BirdBody {

    @Id
    private Integer birdId;

    private String body;
    private String head;
    private String tail;
    private String beak;
    private String eyes;
    private String necklace;
    private String cardSf;
    private String background;
    private String foreground;
    private String headwar;
    private String backpack;
    private String objectLeft;
    private String objectRight;

    private String gemEmpty1;
    private String gemEmpty2;
    private String gemEmpty3;
    private String gemEmpty4;
    private String gemEmpty5;

    private String footLeft;
    private String footRight;
    private String handLeft;
    private String handRight;
    private String wingLeft;
    private String wingRight;

}
