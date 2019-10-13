package me.zhengjie.modules.system.utils;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mr.LkZ
 * @version 2019/10/1217:08
 */
@Slf4j
@Component
public class DataPermissionUtils {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    public HashMap getUserDp(HttpServletRequest request){
        final String requestHeader = request.getHeader(this.tokenHeader);


        String url = "http://localhost:9090/api/user/admin";
        HttpHeaders headers = new HttpHeaders();
        headers.add(this.tokenHeader, requestHeader);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List> entity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, List.class);
        return null;
    }
}
