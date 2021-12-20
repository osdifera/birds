package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Resources_aggregate {
    private Aggregate aggregate;
    @JsonProperty("__typename")
    private String __typename;
}
