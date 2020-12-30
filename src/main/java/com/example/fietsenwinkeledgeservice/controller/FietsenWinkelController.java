package com.example.fietsenwinkeledgeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FietsenWinkelController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${fietsenservice.baseurl}")
    private String fietsenServiceBaseUrl;

    @Value("${onderdeelservice.baseurl}")
    private String onderdeelServiceBaseUrl;

    @Value("${bestellingservice.baseurl}")
    private String bestellingServiceBaseUrl;

    @Value("${klantservice.baseurl}")
    private String klantServiceBaseUrl;


}
