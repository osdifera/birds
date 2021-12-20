package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Base_theme {
    @JsonProperty("theme")
    private Base_theme_theme theme;
    @JsonProperty("__typename")
    private String __typename;
}
