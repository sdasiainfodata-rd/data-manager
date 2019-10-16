package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Test0;
import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
import me.zhengjie.modules.system.service.dto.Test0DTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-08
 */
@CacheConfig(cacheNames = "test0")
public interface Test0Service {

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    Test0DTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    Test0DTO create(Test0 resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Test0 resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * permission tree
     * @return
     */
    @Cacheable(key = "'tree'")
    Object getTest0Tree(List<Test0> test0s);

    /**
     * findByPid
     * @param pid
     * @return
     */
    @Cacheable(key = "'pid:'+#p0")
    List<Test0> findByPid(long pid);

    /**
     * build Tree
     * @param test0DTOS
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object buildTree(List<Test0DTO> test0DTOS);

    /**
     * queryAll
     * @param criteria
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<Test0DTO> queryAll(CommonQueryCriteria criteria);
}
