package me.zhengjie;

import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.UrlDTO;
import me.zhengjie.modules.system.service.impl.UrlServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EladminSystemApplicationTests {
    @Autowired
    private UrlServiceImpl urlService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        String url = "http://localhost:9090/user/admin";
//        UrlDTO byId = urlService.findById(5L);
//        System.out.println(byId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU3MTM4Njg3MCwiaWF0IjoxNTcwNzgyMDcwfQ.g17IflXN6hQfTDkHuwMcMMFySupF-PCpicB_5hc_jowXK2VYguwPZMbtu8w1oFYvkgCMUgAVupf209fE45hALg");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<HashMap> entity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, HashMap.class);
        System.out.println(entity.getBody());
    }

}

