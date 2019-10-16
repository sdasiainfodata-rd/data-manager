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
@Table(name = "test0")
public class Test0 implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(groups = {Update.class})
	private Long id;

	@NotBlank
	private String name;

	/**
	 * 上级类目
	 */
	@NotNull
	@Column(name = "pid",nullable = false)
	private Long pid;


	public interface Update{}
}
