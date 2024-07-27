package com.krs.knowledgerevisingsystem.service;

public interface ReCaptchaService {
    boolean verifyReCaptcha(String recaptchaResponse);
    String getSiteKey();
}
