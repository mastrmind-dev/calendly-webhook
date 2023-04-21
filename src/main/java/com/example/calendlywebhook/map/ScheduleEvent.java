package com.example.calendlywebhook.map;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScheduleEvent {
    @JsonProperty("resource")
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public long getMeetingId() {
        Resource resource = getResource();
        return resource.getMeetingId();
    }
}

class Resource {

    @JsonProperty("calendar_event")
    private Object calendarEvent;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("event_guests")
    private List<Object> eventGuests;

    @JsonProperty("event_memberships")
    private List<EventMembership> eventMemberships;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("invitees_counter")
    private InviteesCounter inviteesCounter;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("name")
    private String name;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("uri")
    private String uri;

    // getters and setters
    public Location getLocation() {
        return location;
    }

    public long getMeetingId() {
        Location location = getLocation();
        LocationData locationData = location.getLocationData();
        long meetingId = locationData.getId();
        return meetingId;
    }
}

class EventMembership {

    @JsonProperty("user")
    private String user;

    // getters and setters
}

class InviteesCounter {

    @JsonProperty("active")
    private int active;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("total")
    private int total;

    // getters and setters
}

class Location {

    @JsonProperty("data")
    private LocationData data;

    @JsonProperty("join_url")
    private String joinUrl;

    @JsonProperty("status")
    private String status;

    @JsonProperty("type")
    private String type;

    // getters and setters
    public LocationData getLocationData() {
        return data;
    }
}

class LocationData {

    @JsonProperty("id")
    private long id;

    @JsonProperty("settings")
    private LocationSettings settings;

    @JsonProperty("extra")
    private LocationExtra extra;

    // getters and setters
    public long getId() {
        return id;
    }
}

class LocationSettings {

    @JsonProperty("global_dial_in_numbers")
    private List<GlobalDialInNumber> globalDialInNumbers;

    // getters and setters
}

class GlobalDialInNumber {

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("number")
    private String number;

    @JsonProperty("type")
    private String type;

    @JsonProperty("country")
    private String country;

    // getters and setters
}

class LocationExtra {

    @JsonProperty("intl_numbers_url")
    private String intlNumbersUrl;

    // getters and setters
}

