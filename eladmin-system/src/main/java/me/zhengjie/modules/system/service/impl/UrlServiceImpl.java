package me.zhengjie.modules.system.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.modules.system.domain.Permission;
import me.zhengjie.modules.system.domain.Url;
import me.zhengjie.modules.system.repository.PermissionRepository;
import me.zhengjie.modules.system.repository.UrlRepository;
import me.zhengjie.modules.system.service.PermissionService;
import me.zhengjie.modules.system.service.UrlService;
import me.zhengjie.modules.system.service.dto.CommonQueryCriteria;
import me.zhengjie.modules.system.service.dto.PermissionDTO;
import me.zhengjie.modules.system.service.dto.UrlDTO;
import me.zhengjie.modules.system.service.mapper.PermissionMapper;
import me.zhengjie.modules.system.service.mapper.UrlMapper;
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
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlMapper urlMapper;

    @Override
    public List<UrlDTO> queryAll(CommonQueryCriteria criteria) {
        return urlMapper.toDto(urlRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public UrlDTO findById(long id) {
        Optional<Url> url = urlRepository.findById(id);
        ValidationUtil.isNull(url,"Url","id",id);
        return urlMapper.toDto(url.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UrlDTO create(Url resources) {
        if(urlRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Url.class,"name",resources.getName());
        }
        return urlMapper.toDto(urlRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Url resources) {
        Optional<Url> optionalUrl = urlRepository.findById(resources.getId());
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        ValidationUtil.isNull(optionalUrl,"url","id",resources.getId());

        Url url = optionalUrl.get();

        Url url1 = urlRepository.findByName(resources.getName());

        if(url1 != null && !url1.getId().equals(url.getId())){
            throw new EntityExistException(Url.class,"name",resources.getName());
        }

//        url.setName(resources.getName());
        url.setName(resources.getName());
        url.setPid(resources.getPid());
        url.setUrl(resources.getUrl());
        urlRepository.save(url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        List<Url> urlList = urlRepository.findByPid(id);
        for (Url url : urlList) {
            urlRepository.delete(url);
        }
        urlRepository.deleteById(id);
    }

    @Override
    public Object getUrlTree(List<Url> urls) {
        List<Map<String,Object>> list = new LinkedList<>();
        urls.forEach(url -> {
                    if (url!=null){
                        List<Url> urlList = urlRepository.findByPid(url.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",url.getId());
                        map.put("label",url.getName());
                        if(urlList!=null && urlList.size()!=0){
                            map.put("children",getUrlTree(urlList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Url> findByPid(long pid) {
        return urlRepository.findByPid(pid);
    }

    @Override
    public Object buildTree(List<UrlDTO> urlDTOS) {

        List<UrlDTO> trees = new ArrayList<UrlDTO>();

        for (UrlDTO urlDTO : urlDTOS) {

            if ("0".equals(urlDTO.getPid().toString())) {
                trees.add(urlDTO);
            }

            for (UrlDTO it : urlDTOS) {
                if (it.getPid().equals(urlDTO.getId())) {
                    if (urlDTO.getChildren() == null) {
                        urlDTO.setChildren(new ArrayList<UrlDTO>());
                    }
                    urlDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = urlDTOS!=null?urlDTOS.size():0;

        Map map = new HashMap();
        map.put("content",trees.size() == 0?urlDTOS:trees);
        map.put("totalElements",totalElements);
        return map;
    }
}
