package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Collection {
    private Integer max;
    @JsonProperty("__typename")
    private String __typename;
}
