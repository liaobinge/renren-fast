package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-04 16:37:24
 */
@Data
@TableName("t_app_user")
public class TAppUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户Id
	 */
	@TableId
	private Long userId;
	/**
	 * 用户名字
	 */
	private String userName;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String headUrl;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
