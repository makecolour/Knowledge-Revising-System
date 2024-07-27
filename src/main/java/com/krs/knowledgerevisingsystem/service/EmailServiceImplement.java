package com.krs.knowledgerevisingsystem.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import okhttp3.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImplement implements EmailService {
    private final OkHttpClient client;
    private final JavaMailSender mailSender;

    public EmailServiceImplement(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.client = new OkHttpClient().newBuilder().build();
    }

    @Override
    public void mailTrapAPI(String from, String to, String name, String subject, String text, String category) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create( mediaType,"{\"from\":{\"email\":\"" + from + ",\"name\":\"" + name + "\"},\"to\":[{\"email\":\"" + to + "\"}],\"subject\":\"" + subject + "\",\"text\":\"" + text + "\",\"category\":\"" + category + "\"}");
        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer a26f744b9342d4c74f51384491417390")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }

    @Override
    public JsonNode mailGunAPI(String domain, String apiKey, String from, String to, String subject, String text) throws Exception {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
			.basicAuth("api", apiKey)
                .queryString("from", from)
                .queryString("to", to)
                .queryString("subject", subject)
                .queryString("text", text)
                .asJson();
        return request.getBody();
    }

    @Override
    public void sendSMTP(String from, String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }


}
