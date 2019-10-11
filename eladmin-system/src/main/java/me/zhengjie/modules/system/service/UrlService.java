package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Permission;
import me.zhengjie.modules.system.domain.Url;
import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
import me.zhengjie.modules.system.service.dto.PermissionDTO;
import me.zhengjie.modules.system.service.dto.UrlDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-08
 */
@CacheConfig(cacheNames = "url")
public interface UrlService {

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UrlDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    UrlDTO create(Url resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Url resources);

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
    Object getUrlTree(List<Url> urls);

    /**
     * findByPid
     * @param pid
     * @return
     */
    @Cacheable(key = "'pid:'+#p0")
    List<Url> findByPid(long pid);

    /**
     * build Tree
     * @param urlDTOS
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object buildTree(List<UrlDTO> urlDTOS);

    /**
     * queryAll
     * @param criteria
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<UrlDTO> queryAll(CommonQueryCriteria criteria);
}
