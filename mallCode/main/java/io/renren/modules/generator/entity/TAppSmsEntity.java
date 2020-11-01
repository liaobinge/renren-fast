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
@TableName("t_app_sms")
public class TAppSmsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 短信Id
	 */
	@TableId
	private Long smsId;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 发送内容
	 */
	private String sendContent;
	/**
	 * 验证码
	 */
	private String smsCode;
	/**
	 * 用途
	 */
	private String smsUse;
	/**
	 * 用户Id
	 */
	private Long smsUserId;
	/**
	 * 查询次数
	 */
	private Integer queryCount;

}
