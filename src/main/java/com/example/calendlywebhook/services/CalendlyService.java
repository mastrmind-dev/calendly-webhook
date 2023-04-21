package com.example.calendlywebhook.services;

import com.example.calendlywebhook.map.CreatorMap;
import com.example.calendlywebhook.map.ScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CalendlyService {

    @Autowired
    private RestTemplate restTemplate;

    private static HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("eyJraWQiOiIxY2UxZTEzNjE3ZGNmNzY2YjNjZWJjY2Y4ZGM1YmFmYThhNjVlNjg0MDIzZjdjMzJiZTgzNDliMjM4MDEzNWI0IiwidHlwIjoiUEFUIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOiJodHRwczovL2F1dGguY2FsZW5kbHkuY29tIiwiaWF0IjoxNjgyMDYzNzg4LCJqdGkiOiJkYjhhNWFiNC00YjNiLTQ1ZjUtOWI2OS1lM2IyYzQ5NjkyZDciLCJ1c2VyX3V1aWQiOiI1YjZlMzJlMi0xMjhhLTQ0YjMtYjUyYS02MTIwMmM1ZDY2NWUifQ.sD99a9gpyvXUEHGTlCR-YzyX_CF3LRth_milkWc--GO3lTF6ioi7Si14ZhKqOsDT-kGe3vAv-ppCBaMKCIgXxQ");
        return headers;
    }

    public long getMeetingId(String event_uri) {
        String apiUrl = event_uri;
        HttpHeaders headers = setHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ScheduleEvent scheduleEvent = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, ScheduleEvent.class).getBody();

        return scheduleEvent.getMeetingId();
    }

    public String getValidatorEmail(String creator_uri) {
        String apiUrl = creator_uri;
        HttpHeaders headers = setHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        CreatorMap creatorMap = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, CreatorMap.class).getBody();

        return creatorMap.getEmail();
    }
}
