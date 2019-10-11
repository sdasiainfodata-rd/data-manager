package me.zhengjie.modules.system.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.Url;
import me.zhengjie.modules.system.service.UrlService;
import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
import me.zhengjie.modules.system.service.dto.UrlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
public class UrlController {

    @Autowired
    private UrlService urlService;


    private static final String ENTITY_NAME = "url";

    /**
     * 返回全部的权限，新增角色时下拉选择
     * @return
     */
    @GetMapping(value = "/urls/tree")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_CREATE','PERMISSION_EDIT','ROLES_SELECT','ROLES_ALL')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity getTree(){
        return new ResponseEntity(urlService.getUrlTree(urlService.findByPid(0L)),HttpStatus.OK);
    }

    @Log("查询权限")
    @GetMapping(value = "/urls")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_SELECT')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity getPermissions(CommonQueryCriteria criteria){
        List<UrlDTO> urlDTOS = urlService.queryAll(criteria);
        return new ResponseEntity(urlService.buildTree(urlDTOS),HttpStatus.OK);
    }

    @Log("新增权限")
    @PostMapping(value = "/urls")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_CREATE')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity create(@Validated @RequestBody Url resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(urlService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改权限")
    @PutMapping(value = "/urls")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_EDIT')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity update(@Validated(Url.Update.class) @RequestBody Url resources){
        urlService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除权限")
    @DeleteMapping(value = "/urls/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_DELETE')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity delete(@PathVariable Long id){
        urlService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
