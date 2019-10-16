package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import me.zhengjie.modules.system.domain.Test0;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Data
public class Test0DTO implements Serializable{

	private Long id;

	private String name;

	private Long pid;

	private List<Test0DTO>  children;
}
