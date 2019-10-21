package me.zhengjie;

import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.Test0Service;
import me.zhengjie.modules.system.service.dto.Test0DTO;
import me.zhengjie.modules.system.service.dto.UrlDTO;
import me.zhengjie.modules.system.service.dto.dp.RoleDP;
import me.zhengjie.modules.system.service.impl.UrlServiceImpl;
import me.zhengjie.modules.utils.RestTemplateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EladminSystemApplicationTests {
    @Autowired
    private UrlServiceImpl urlService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private Test0Service test0Service;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Test
    public void contextLoads() {
//        UrlDTO byId = urlService.findById(5L);
//        System.out.println(byId);
//        Test0DTO byId1 = test0Service.findById(3);
//        System.out.println(byId1);
        String url = "https://localhost:8888/index";
//        UrlDTO byId = urlService.findById(5L);
//        System.out.println(byId);
        HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU3MTM4Njg3MCwiaWF0IjoxNTcwNzgyMDcwfQ.g17IflXN6hQfTDkHuwMcMMFySupF-PCpicB_5hc_jowXK2VYguwPZMbtu8w1oFYvkgCMUgAVupf209fE45hALg");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<HashMap> entity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, HashMap.class);
        System.out.println(entity.getBody());

    }

    @Test
    public void testUtils(){
//        Object o = restTemplateUtils.sendGet("https://localhost:9090/api/news/page/1", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQWRtaW4iLCJleHAiOjE1NzIyMzAwNDYsImlhdCI6MTU3MTYyNTI0Nn0.ztlazCfkOoON_fq8ocSPKSmlr4K5SEAjwOiGc5c9JpV2OesdTpbeJRg_S6Y3J-FIycpY0YI2IkthmZdxICXr-g");
//        System.out.println(o);
//        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//        map.add("role_name", "testUtils");
//        map.add("permissions", "testUtils");
        RoleDP roleDP = new RoleDP();
        roleDP.set_id("5dad41f0fdf52e0f44e4dd27");
        roleDP.setRoleName("roleUtils111");
        ArrayList<String> list = new ArrayList<>();
        list.add("roleUtils111");
        roleDP.setPermissions(list);
        Object post = restTemplateUtils.sendPut("https://localhost:9090/roles", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQWRtaW4iLCJleHAiOjE1NzIyMzAwNDYsImlhdCI6MTU3MTYyNTI0Nn0.ztlazCfkOoON_fq8ocSPKSmlr4K5SEAjwOiGc5c9JpV2OesdTpbeJRg_S6Y3J-FIycpY0YI2IkthmZdxICXr-g", roleDP);
        System.out.println(post);

    }

}

