package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Resources_base_themes {
    private Theme theme;
    @JsonProperty("__typename")
    private String __typename;
}
