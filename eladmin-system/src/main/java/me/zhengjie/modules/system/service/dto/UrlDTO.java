package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Data
public class UrlDTO implements Serializable{

	private Long id;

	private String url;

	private Long pid;

	private String name;

	private Timestamp createTime;

	private List<UrlDTO>  children;

	@Override
	public String toString() {
		return "UrlDTO{" +
				"id=" + id +
				", url='" + url + '\'' +
				", pid=" + pid +
				", name='" + name + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
