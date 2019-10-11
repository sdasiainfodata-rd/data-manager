package me.zhengjie.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Entity
@Getter
@Setter
@Table(name = "url")
public class Url implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(groups = {Update.class})
	private Long id;

	@NotBlank
	private String url;

	/**
	 * 上级类目
	 */
	@NotNull
	@Column(name = "pid",nullable = false)
	private Long pid;

	@NotBlank
	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "urls")
	private Set<Role> roles;

	@CreationTimestamp
	@Column(name = "create_time")
	private Timestamp createTime;

	@Override
	public String toString() {
		return "Url{" +
				"id=" + id +
				", url='" + url + '\'' +
				", pid=" + pid +
				", name='" + name + '\'' +
				", createTime=" + createTime +
				'}';
	}

	public interface Update{}
}
