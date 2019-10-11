package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
public interface UrlRepository extends JpaRepository<Url, Long>, JpaSpecificationExecutor {

    /**
     * findByUrl
     * @param url
     * @return
     */
    Url findByUrl(String url);

    /**
     * findByName
     * @param name
     * @return
     */
    Url findByName(String name);

    /**
     * findByPid
     * @param pid
     * @return
     */
    List<Url> findByPid(long pid);

}
