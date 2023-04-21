package com.example.calendlywebhook.services;

import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomService {

    @Value("${zoom.api.key}")
    private String apiKey;

    @Value("${zoom.api.secret}")
    private String apiSecret;

    @Value("${zoom.account.id}")
    private String accountId;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ZoomService.class);
    private static final String UPDATE_MEETING_URL = "https://api.zoom.us/v2/meetings/{meetingId}";

    public HttpStatusCode updateMeetingWithNewUserEmail(@NotEmpty String newUserEmail, @NotEmpty String meetingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getZoomAccessToken());
        headers.set("Content-Type", "application/json");

        System.out.println("validator " + newUserEmail);

//        this code block is only for poc testing
        if (newUserEmail != "bhavan@kayatech.com" && newUserEmail != "achilal@techlabs.tech" && newUserEmail != "nalinaw@techlabs.tech") {
            System.out.println(newUserEmail + " is changed to bhavan@kayatech.com since " + newUserEmail + " is not a licensed zoom user.");
            newUserEmail = "bhavan@kayatech.com";
        }
//        poc testing code block ends


        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("schedule_for", newUserEmail);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(UPDATE_MEETING_URL, HttpMethod.PATCH, requestEntity, Map.class, meetingId);

        LOGGER.info("status code from meeting update endpoint: {}", response.getStatusCode());

        return response.getStatusCode();
    }

    public String getZoomAccessToken() {
//        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ag4TfVisSkW11VMLHrcYQ", "07I6QKBOZJ7fS7xOdT0lQHK2NRrW4rLk");
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "account_credentials");
        requestBody.add("account_id", "_a3LWzYfRQW8OV8B_mYFUQ");

        HttpEntity<MultiValueMap<String, String>> requestEntitiy = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = null;
        try {
            response = restTemplate.postForEntity("https://api.zoom.us/oauth/token", requestEntitiy, Map.class);
        } catch (Exception e) {
            System.out.println("error=>");
            System.out.println(e);
        }

        if (response.getBody().containsKey("access_token")) {
            return response.getBody().get("access_token").toString();
        } else {
            throw new RuntimeException("Zoom access token fetching is failed!");
        }
    }
}
