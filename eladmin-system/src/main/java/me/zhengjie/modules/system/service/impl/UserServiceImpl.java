package me.zhengjie.modules.system.service.impl;

import com.sun.corba.se.spi.ior.ObjectId;
import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.modules.security.utils.JwtTokenUtil;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDTO;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.modules.system.service.dto.dp.UserDP;
import me.zhengjie.modules.system.service.mapper.UserMapper;
import me.zhengjie.modules.utils.MapBeanUtils;
import me.zhengjie.modules.utils.RestTemplateUtils;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RestTemplateUtils restTemplateUtils;
    @Value("${remote.server.url}")
    private String host;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Object queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<User> page = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        Page<UserDTO> pageDTO = (Page<UserDTO>) page.map(userMapper::toDto);
        List<UserDTO> userDTOList = pageDTO.getContent();

        String token = getCurrentUserToken();
        if (userDTOList == null||StringUtils.isEmpty(token)) return null;
        //遍历userDTOList,设置userDp属性
        for (UserDTO userDTO : userDTOList) {
            String username = userDTO.getUsername();
            String url = host+"/users/"+username;
            //访问数据中台
            HashMap userDp = (HashMap) restTemplateUtils.sendGet(url, token);
            System.out.println(userDp);
            userDTO.setUserDP(userDp);
        }

        return PageUtil.toPage(pageDTO);
    }

    private String getCurrentUserToken() {
        //获取当前登录的用户名
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isEmpty(user)) return null;
        //生成当前登录用户的token
        return jwtTokenUtil.generateToken(user);
    }

    @Override
    public UserDTO findById(long id) {
        Optional<User> user = userRepository.findById(id);
        ValidationUtil.isNull(user,"User","id",id);
        return userMapper.toDto(user.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(UserDTO resources) {

        if(userRepository.findByUsername(resources.getUsername())!=null){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }

        if(userRepository.findByEmail(resources.getEmail())!=null){
            throw new EntityExistException(User.class,"email",resources.getEmail());
        }

        // 默认密码 123456，此密码是加密后的字符
        System.out.println("密码:"+resources.getPassword());
//        resources.setPassword("e10adc3949ba59abbe56e057f20f883e");
        String password = resources.getPassword();
        if (password!=null) {
            String encodePassword = new BCryptPasswordEncoder().encode(password);
            resources.setPassword(encodePassword);
        }else {
            resources.setPassword("$2a$10$.inJIttGOsuxAhU81jVR2eQMEmetEKDY11Kbm20f70RR4VqKJuqmy");
        }
        resources.setAvatar("https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg");
        //
        HashMap userDPMap = resources.getUserDP();
        if (!CollectionUtils.isEmpty(userDPMap)){
            UserDP userDP = MapBeanUtils.map2Object(userDPMap, UserDP.class);
            String token = getCurrentUserToken();
            if (token != null) {
                String url = host+"/users";
                restTemplateUtils.sendPost(url,token ,userDP );
            }
        }

        User user = userMapper.toEntity(resources);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserDTO resources) {
        HashMap userDPMap = resources.getUserDP();
        if (!CollectionUtils.isEmpty(userDPMap)){
            if (userDPMap.get("id")==null||StringUtils.isEmpty(userDPMap.get("id").toString())) return;
            UserDP userDP = MapBeanUtils.map2Object(userDPMap, UserDP.class);
            String token = getCurrentUserToken();
            if (token != null) {
                String url = host+"/users";
                restTemplateUtils.sendPut(url,token ,userDP );
            }
        }


        User entity = userMapper.toEntity(resources);
        Optional<User> userOptional = userRepository.findById(entity.getId());
        ValidationUtil.isNull(userOptional,"User","id",entity.getId());

        User user = userOptional.get();

        User user1 = userRepository.findByUsername(user.getUsername());
        User user2 = userRepository.findByEmail(user.getEmail());

        if(user1 !=null&&!user.getId().equals(user1.getId())){
            throw new EntityExistException(User.class,"username",entity.getUsername());
        }

        if(user2!=null&&!user.getId().equals(user2.getId())){
            throw new EntityExistException(User.class,"email",entity.getEmail());
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!entity.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisService.delete(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisService.delete(key);
        }

        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setEnabled(entity.getEnabled());
        user.setRoles(entity.getRoles());
        user.setDept(entity.getDept());
        user.setJob(entity.getJob());
        user.setPhone(entity.getPhone());
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {

        Optional<User> byId = userRepository.findById(id);
        User user = userRepository.findById(id).get();
        if (user==null)return;
        String token = getCurrentUserToken();
        String username = user.getUsername();
        String url = host+"/users/"+username;
        //访问数据中台
        HashMap userDp = (HashMap) restTemplateUtils.sendGet(url, token);

        String mongoId = (String) userDp.get("id");
        System.out.println(mongoId);
        String deleteUrl = host + "/users/"+mongoId;
        System.out.println(deleteUrl);
        restTemplateUtils.sendDelete(deleteUrl,token );

        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findByName(String userName) {
        User user = null;
        if(ValidationUtil.isEmail(userName)){
            user = userRepository.findByEmail(userName);
        } else {
            user = userRepository.findByUsername(userName);
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        userRepository.updatePass(username,pass,new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String url) {
        userRepository.updateAvatar(username,url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userRepository.updateEmail(username,email);
    }
}
