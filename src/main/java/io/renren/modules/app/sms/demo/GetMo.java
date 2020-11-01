package io.renren.modules.app.sms.demo;

import io.renren.modules.app.sms.client.SmsClient;
import io.renren.modules.app.sms.vo.ResultMsg;

/**
 * 获取上行记录示例
 */
public class GetMo {
	public static void main(String[] args) {
		String custCode = "xxxxx";							 //[必填] 用户账号
		String password = "xxxxxxxx";						 //[必填] 账号密码
		String serviceBaseUrl = "https://ip:port"; 			 //[必填] https://ip:port

		SmsClient smsClient = new SmsClient();
		ResultMsg resultMsg = smsClient.getMo(custCode, password, serviceBaseUrl);
		if (resultMsg.isSuccess()) { 
			/**
			 * 成功返回json数组字符串，data数据如下：
			 * [
				    {
				        "smsLabel": "",
				        "recv_time": "2017-12-22 09:57:41",
				        "msg_content": "ddcfffffcf",
				        "sp_code": "106903510107281",
				        "src_mobile": "13489080110",
				        "msg_id": "-3807905081491652607"
				    },
				    {
				        "smsLabel": "",
				        "recv_time": "2017-12-22 09:57:44",
				        "msg_content": "sscgvcd",
				        "sp_code": "106903510107281",
				        "src_mobile": "13489080110",
				        "msg_id": "-3807904256858324992"
				    }
				]
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
