package com.example.calendlywebhook.controllers;

import com.example.calendlywebhook.services.CalendlyService;
import com.example.calendlywebhook.services.ZoomService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookController.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final CalendlyService calendlyService;
    private final ZoomService zoomService;

    public WebhookController(CalendlyService calendlyService, ZoomService zoomService) {
        this.calendlyService = calendlyService;
        this.zoomService = zoomService;
    }

    @PostMapping("/calendly-webhook")
    public void handleWebhook(@RequestBody String payload) {
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(payload);

            String event = jsonNode.get("event").asText();
            LOGGER.info("jsonNode:{}", jsonNode);

            String event_uri = jsonNode.get("payload").get("event").asText();
            LOGGER.info("event_uri:{}", event_uri);

            String creator_uri = jsonNode.get("created_by").asText();
            LOGGER.info("creator_uri:{}", creator_uri);

            long meetingId = calendlyService.getMeetingId(event_uri);
            LOGGER.info("Received Calendly webhook for event: {}, event_uri: {}, meeting_id: {}", event, event_uri, meetingId);

            String validatorEmail = calendlyService.getValidatorEmail(creator_uri);
            HttpStatusCode responseFromZoomService = zoomService.updateMeetingWithNewUserEmail(validatorEmail, Long.toString(meetingId));

            if (responseFromZoomService.value() == 204) {
                LOGGER.info("Meeting successfully updated.");
            } else {
                LOGGER.error("Meeting updating failed!");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to handle Calendly webhook payload: {}", payload, e);
        }
    }
}

