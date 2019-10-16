package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.domain.Test0;
import me.zhengjie.modules.system.domain.Url;
import me.zhengjie.modules.system.service.dto.Test0DTO;
import me.zhengjie.modules.system.service.dto.UrlDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Repository;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Repository
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Test0Mapper extends EntityMapper<Test0DTO, Test0> {

}
