package com.krs.knowledgerevisingsystem.service;

import kong.unirest.JsonNode;

public interface EmailService {
    void mailTrapAPI(String from, String to, String name, String subject, String text, String category) throws Exception;
    JsonNode mailGunAPI(String domain, String apiKey, String from, String to, String subject, String text) throws Exception;
    void sendSMTP (String from, String to, String subject, String body);
}
