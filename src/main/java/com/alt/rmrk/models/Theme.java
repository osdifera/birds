package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Theme {
    private String id;
    @JsonProperty("__typename")
    private String __typename;
}
