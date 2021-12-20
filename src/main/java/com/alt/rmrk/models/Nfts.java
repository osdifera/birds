package com.alt.rmrk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Nfts {
    private String id;
    private Integer block;
    private String burned;
    private Long forsale;
    private String collectionId;
    private String symbol;
    private String metadata;
    private String owner;
    private String rootowner;
    private String sn;
    private Integer transferable;
    private List<String> priority;
    private String metadata_image;
    private String metadata_description;
    private String metadata_name;
    private String updated_at;
    private Resources_aggregate resources_aggregate;
    private List<Resources> resources;
    private Collection collection;
    @JsonProperty("__typename")
    private String __typename;
}
