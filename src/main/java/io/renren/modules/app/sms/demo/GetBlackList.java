package io.renren.modules.app.sms.demo;

import io.renren.modules.app.sms.client.SmsClient;
import io.renren.modules.app.sms.vo.ResultMsg;

/**
 * 获取黑名单示例
 */
public class GetBlackList {
	public static void main(String[] args) {
		String custCode = "xxxxx";							 //[必填] 用户账号
		String password = "xxxxxxxx";						 //[必填] 账号密码
		String serviceBaseUrl = "https://ip:port"; 			 //[必填] https://ip:port
		
		String mobile = "18778480787";						 //手机号码

		SmsClient smsClient = new SmsClient();
		ResultMsg resultMsg = smsClient.getBlacklist(custCode, password, serviceBaseUrl, mobile);
		if (resultMsg.isSuccess()) { 
			/**
			 * 返回该手机的黑名单列表
			 * type:1.网关黑名单
			 *      2.禁一年黑名单
			 *      3.投诉黑名单
			 *      4.退订黑名单
			 * [
			 * {"create_time":"2017-12-29 10:24:58","cust_id":0,"mobile":"18778480787","smsLabel":"none","type":"1"},
			 * {"create_time":"2017-12-29 10:23:47","cust_id":2283,"mobile":"18778480787","smsLabel":"none","type":"3"},
			 * {"create_time":"2016-11-11 19:28:34","cust_id":2283,"mobile":"18778480787","note":"客户发送退订指令[td]","smsLabel":"85","type":"4"},
			 * {"create_time":"2017-12-29 10:24:32","cust_id":2283,"mobile":"18778480787","smsLabel":"none","type":"4"}
			 * ]
			 */
			System.out.println(resultMsg.getData());
		} else {
			/**
			 * 1000：服务器出现未知异常！
			 * 1001 操作不合法，操作前未获取Token，或Token已过时
			 * 1002 签名验证不通过！
			 * 1003Json参数解析出错
			 * 1004 操作不合法，cust_code: xxxxxx不存在
			 */
			System.out.println(resultMsg.getCode());
			System.out.println(resultMsg.getMsg());
		}
	}
}
