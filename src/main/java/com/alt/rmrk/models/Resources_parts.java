package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Resources_parts {
    private Part part;
    @JsonProperty("__typename")
    private String __typename;
}
