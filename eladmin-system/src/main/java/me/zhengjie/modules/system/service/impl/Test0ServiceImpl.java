package me.zhengjie.modules.system.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.modules.system.domain.Test0;
import me.zhengjie.modules.system.repository.Test0Repository;
import me.zhengjie.modules.system.service.Test0Service;
import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
import me.zhengjie.modules.system.service.dto.Test0DTO;
import me.zhengjie.modules.system.service.mapper.Test0Mapper;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class Test0ServiceImpl implements Test0Service {

    @Autowired
    private Test0Repository test0Repository;

    @Autowired
    private Test0Mapper test0Mapper;

    @Override
    public List<Test0DTO> queryAll(CommonQueryCriteria criteria) {
        System.out.println();
        return test0Mapper.toDto(test0Repository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public Test0DTO findById(long id) {
        Optional<Test0> test0 = test0Repository.findById(id);
        System.out.println();
        ValidationUtil.isNull(test0,"Test0","id",id);
        return test0Mapper.toDto(test0.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Test0DTO create(Test0 resources) {
        if(test0Repository.findByName(resources.getName()) != null){
            throw new EntityExistException(Test0.class,"name",resources.getName());
        }
        return test0Mapper.toDto(test0Repository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Test0 resources) {
        Optional<Test0> optionalTest0 = test0Repository.findById(resources.getId());
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        ValidationUtil.isNull(optionalTest0,"test0","id",resources.getId());

        Test0 test0 = optionalTest0.get();

        Test0 test01 = test0Repository.findByName(resources.getName());

        if(test01 != null && !test01.getId().equals(test0.getId())){
            throw new EntityExistException(Test0.class,"name",resources.getName());
        }

//        test0.setName(resources.getName());
        test0.setName(resources.getName());
        test0.setPid(resources.getPid());
//        test0.setTest0(resources.getTest0());
        test0Repository.save(test0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        List<Test0> test0List = test0Repository.findByPid(id);
        for (Test0 test0 : test0List) {
            test0Repository.delete(test0);
        }
        test0Repository.deleteById(id);
    }

    @Override
    public Object getTest0Tree(List<Test0> test0s) {
        List<Map<String,Object>> list = new LinkedList<>();
        test0s.forEach(test0 -> {
                    if (test0!=null){
                        List<Test0> test0List = test0Repository.findByPid(test0.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",test0.getId());
                        map.put("label",test0.getName());
                        if(test0List!=null && test0List.size()!=0){
                            map.put("children",getTest0Tree(test0List));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Test0> findByPid(long pid) {
        return test0Repository.findByPid(pid);
    }

    @Override
    public Object buildTree(List<Test0DTO> test0DTOS) {

        List<Test0DTO> trees = new ArrayList<Test0DTO>();

        for (Test0DTO test0DTO : test0DTOS) {

            if ("0".equals(test0DTO.getPid().toString())) {
                trees.add(test0DTO);
            }

            for (Test0DTO it : test0DTOS) {
                if (it.getPid().equals(test0DTO.getId())) {
                    if (test0DTO.getChildren() == null) {
                        test0DTO.setChildren(new ArrayList<Test0DTO>());
                    }
                    test0DTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = test0DTOS!=null?test0DTOS.size():0;

        Map map = new HashMap();
        map.put("content",trees.size() == 0?test0DTOS:trees);
        map.put("totalElements",totalElements);
        return map;
    }
}
