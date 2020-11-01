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
 * @date 2020-08-04 16:37:25
 */
@Data
@TableName("t_app_authorize")
public class TAppAuthorizeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 授权Id
	 */
	@TableId
	private Long auId;
	/**
	 * 用户Id
	 */
	private Long userId;
	/**
	 * 微博授权
	 */
	private String weiboAu;
	/**
	 * 微信授权
	 */
	private String wechatAu;
	/**
	 * QQ授权
	 */
	private String qqAu;
	/**
	 * 斑马授权
	 */
	private String banmaAu;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
