package me.zhengjie.modules.utils;

import me.zhengjie.modules.system.service.dto.dp.RoleDP;
import me.zhengjie.modules.system.service.dto.dp.UserDP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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

    public Object sendPost(String url, String Token,  UserDP body){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<UserDP> requestEntity = new HttpEntity<>(body,headers);
        return  restTemplate.postForEntity(url,requestEntity ,HashMap.class ).getBody();
    }

    public Object sendPost(String url, String Token,  RoleDP body){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<RoleDP> requestEntity = new HttpEntity<>(body,headers);
        return  restTemplate.postForEntity(url,requestEntity ,HashMap.class ).getBody();
    }

    public Object sendPut(String url,String Token,UserDP body){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<UserDP> requestEntity = new HttpEntity<>(body,headers);
        return  restTemplate.exchange(url, HttpMethod.PUT, requestEntity, HashMap.class).getBody();
    }

    public Object sendPut(String url,String Token,RoleDP body){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<RoleDP> requestEntity = new HttpEntity<>(body,headers);
        return  restTemplate.exchange(url, HttpMethod.PUT, requestEntity, HashMap.class).getBody();
    }

    public void sendDelete(String url,String Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+Token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, HashMap.class).getBody();
    }
}
