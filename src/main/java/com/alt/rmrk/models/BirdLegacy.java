package com.alt.rmrk.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="backup_birds")
public class BirdLegacy {
    @Id
    private Integer id;
    private String url;
    private String name;
    private Integer price;
    private Integer fullset;
}
