package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Test0;
import me.zhengjie.modules.system.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
public interface Test0Repository extends JpaRepository<Test0, Long>, JpaSpecificationExecutor {


    /**
     * findByName
     * @param name
     * @return
     */
    Test0 findByName(String name);

    /**
     * findByPid
     * @param pid
     * @return
     */
    List<Test0> findByPid(Long pid);

}
