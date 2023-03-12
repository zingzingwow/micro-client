package com.car.client.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {


    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplateBuilder templateBuilder;

    private static final String SAY_HELLO_URL = "/api/service/sayHello";

    @RequestMapping(SAY_HELLO_URL)
    public String callService(){
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("service", false);
        String url = instance.getHomePageUrl();
        RestTemplate restTemplate = templateBuilder.build();
        ResponseEntity<String> response = restTemplate.exchange(url + SAY_HELLO_URL, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
