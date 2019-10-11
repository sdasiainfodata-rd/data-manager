package me.zhengjie;

import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.UrlDTO;
import me.zhengjie.modules.system.service.impl.UrlServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EladminSystemApplicationTests {
    @Autowired
    private UrlServiceImpl urlService;
    @Autowired
    private RoleService roleService;

    @Test
    public void contextLoads() {
        UrlDTO byId = urlService.findById(5L);
        System.out.println(byId);
    }

}

