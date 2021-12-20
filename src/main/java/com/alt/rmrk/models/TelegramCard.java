package com.alt.rmrk.models;

import lombok.Data;

@Data
public class TelegramCard {
    private String thumb;
    private Integer oldPrice;
    private Integer newPrice;
    private String birdName;
}
