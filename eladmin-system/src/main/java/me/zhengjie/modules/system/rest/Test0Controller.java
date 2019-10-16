package me.zhengjie.modules.system.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.Test0;
import me.zhengjie.modules.system.service.Test0Service;
import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
import me.zhengjie.modules.system.service.dto.Test0DTO;
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
public class Test0Controller {

    @Autowired
    private Test0Service test0Service;


    private static final String ENTITY_NAME = "test0";

    /**
     * 返回全部的权限，新增角色时下拉选择
     * @return
     */
    @GetMapping(value = "/test0s/tree")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_CREATE','PERMISSION_EDIT','ROLES_SELECT','ROLES_ALL')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity getTree(){
        return new ResponseEntity(test0Service.getTest0Tree(test0Service.findByPid(0L)),HttpStatus.OK);
    }

    @Log("查询权限")
    @GetMapping(value = "/test0s")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_SELECT')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity getPermissions(CommonQueryCriteria criteria){
        List<Test0DTO> test0DTOS = test0Service.queryAll(criteria);
        return new ResponseEntity(test0Service.buildTree(test0DTOS),HttpStatus.OK);
    }

    @Log("新增权限")
    @PostMapping(value = "/test0s")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_CREATE')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity create(@Validated @RequestBody Test0 resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(test0Service.create(resources),HttpStatus.CREATED);
    }

    @Log("修改权限")
    @PutMapping(value = "/test0s")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_EDIT')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity update(@Validated(Test0.Update.class) @RequestBody Test0 resources){
        test0Service.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除权限")
    @DeleteMapping(value = "/test0s/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_DELETE')")
//    @PreAuthorize("permitAll()")
    public ResponseEntity delete(@PathVariable Long id){
        test0Service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
