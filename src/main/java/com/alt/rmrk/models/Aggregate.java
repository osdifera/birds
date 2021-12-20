package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Aggregate {
    private Integer count;
    @JsonProperty("__typename")
    private String __typename;
}
