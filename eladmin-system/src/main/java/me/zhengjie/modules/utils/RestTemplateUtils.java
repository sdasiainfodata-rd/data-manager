package me.zhengjie.modules.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author Mr.LkZ
 * @version 2019/10/2111:22
 */
@Component
public class RestTemplateUtils {
    @Autowired
    private RestTemplate restTemplate;

    public Object sendGet(String url,String Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return  restTemplate.exchange(url, HttpMethod.GET, requestEntity, HashMap.class).getBody();
    }

    public Object sendPost(String url,String Token,LinkedMultiValueMap body){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body,headers);
        return  restTemplate.exchange(url, HttpMethod.POST, requestEntity, HashMap.class).getBody();
    }

    public Object sendPut(String url,String Token,LinkedMultiValueMap body){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body,headers);
        return  restTemplate.exchange(url, HttpMethod.PUT, requestEntity, HashMap.class).getBody();
    }

    public void sendDelete(String url,String Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, HashMap.class).getBody();
    }
}
