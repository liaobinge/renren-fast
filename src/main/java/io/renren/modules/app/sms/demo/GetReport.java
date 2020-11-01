package io.renren.modules.app.sms.demo;

import io.renren.modules.app.sms.client.SmsClient;
import io.renren.modules.app.sms.vo.ResultMsg;

/**
 * 获取状态报告示例
 */
public class GetReport {
	public static void main(String[] args) {
		String custCode = "xxxxx";							 //[必填] 用户账号
		String password = "xxxxxxxx";						 //[必填] 账号密码
		String serviceBaseUrl = "https://ip:port"; 			 //[必填] https://ip:port

		SmsClient smsClient = new SmsClient();
		ResultMsg resultMsg = smsClient.getReport(custCode, password, serviceBaseUrl);
		if (resultMsg.isSuccess()) {
			/**
			 * 成功返回json数组字符串，data数据如下：
			 * [
				    {
				        "msgid": "59106412211644219176",
				        "mobile": "1348908xxxx",
				        "report_status": "SUCCESS",
				        "report": "DELIVRD",
				        "uid": "1123344567",
				        "recv_time": "2017-12-21 16:44:31"
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
