package me.zhengjie.modules.system.service.dto.dp;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Mr.LkZ
 * @version 2019/10/812:30
 */
@Data
public class UserDP {
    private String _id;
    private String username;
    private Date createTime; // datetime DEFAULT NULL COMMENT '创建日期',
    private Date lastUpdateTime; // datetime DEFAULT NULL COMMENT '最后更能日期',
    private List<String> dataRoles;
//    @Field("collection_feilds")
//    private HashMap<String,Set<String>> collectionFeilds;
    private boolean isDelete;
}
