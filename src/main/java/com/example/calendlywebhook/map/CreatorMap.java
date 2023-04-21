package com.example.calendlywebhook.map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class CreatorMap {
    @JsonProperty("resource")
    private Resource resource;

    private static class Resource {
        @JsonProperty("email")
        private String email;

        private String getEmail() {
            return email;
        }
    }

    public String getEmail() {
        return resource.getEmail();
    }

}
