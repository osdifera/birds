package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Resources {
    private String base_id;
    private String id;
    private String src;
    private String slot_id;
    private String metadata;
    private String thumb;
    private String theme;
    private Base_theme base_theme;
    private List<Resources_parts> resources_parts;
    private List<Resources_base_themes> resources_base_themes;
    @JsonProperty("__typename")
    private String __typename;
}
