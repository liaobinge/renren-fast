package io.renren.modules.app.sms.demo;


import io.renren.modules.app.sms.client.SmsClient;
import io.renren.modules.app.sms.vo.ResultMsg;
import io.renren.modules.app.sms.vo.VariantParams;
import io.renren.modules.app.sms.vo.VariantSmsReq;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送变量短信示例
 */
public class SendVariantSms {
	public static void main(String[] args) {
		String custCode = "xxxxx";							 //[必填] 用户账号
		String password = "xxxxxxxx";						 //[必填] 账号密码
		String serviceBaseUrl = "https://ip:port"; 			 //[必填] https://ip:port
		/**
		 * 1.通过SmsReq对象传参
		 */
		//变量参数
		List<VariantParams> variantParamsList = new ArrayList<VariantParams>();
		String[] vars = {"福州","21"};//对应内容中的${var1}和${var2}
		String[] vars1 = {"哈尔滨","-21"};//对应内容中的${var1}和${var2}
		String[] vars2 = {"海南","30"};//对应内容中的${var1}和${var2}
		
		VariantParams variantParams = new VariantParams();
		variantParams.setMobile("13489080110");
		variantParams.setVars(vars);
		variantParamsList.add(variantParams);
		
		VariantParams variantParams1 = new VariantParams();
		variantParams1.setMobile("13489080111");
		variantParams1.setVars(vars1);
		variantParamsList.add(variantParams1);
		
		VariantParams variantParams2 = new VariantParams();
		variantParams2.setMobile("13489080112");
		variantParams2.setVars(vars2);
		variantParamsList.add(variantParams2);
		//变量短信请求
		VariantSmsReq variantSmsReq = new VariantSmsReq();
		variantSmsReq.setUid("1123344567");					//[选填] 业务标识，由贵司自定义32为数字透传至我司
		variantSmsReq.setCust_code(custCode);				//[必填] 用户账号
		variantSmsReq.setContent("${var1}天气${var2}度");		//[必填] 短信模板。其中的变量用“${vari}”来替代,i代表变量的序号（从0开始增长，每次增加1）。例如：“${var0}用户您好，今天${var1}的天气，晴，温度${var2}度，事宜外出。”，该短信中具有两个变量参数。编码为UTF-8格式。
		variantSmsReq.setParams(variantParamsList);			//[必填] 同时发送给多个号码时,号码之间用英文半角逗号分隔(,),，其中变量短信每一组为json格式，如: [{"mobile"："手机号码","var":["福州","30"]},{"mobile"："手机号码","var":["厦门","32"]}]	每组变量中第一个变量固定为目标手机号码，对应短信模板中的参数，var为变量个数要与内容中的(其中i变量系好，从1开始)个数匹配，以此类推。（请注意：变量中不要包含有逗号和竖线，否则发送的格式解析会有问题）
		variantSmsReq.setNeed_report("yes");				//[选填] 状态报告需求与否，是 yes 否 no 默认yes
		variantSmsReq.setSp_code("");						//[选填] 长号码
		variantSmsReq.setMsgFmt("8");						//[选填] 信息格式，0：ASCII串；3：短信写卡操作；4：二进制信息；8：UCS2编码；默认8

		SmsClient smsClient = new SmsClient();
		ResultMsg resultMsg = smsClient.sendVariantSms(variantSmsReq, serviceBaseUrl, password);
		if (resultMsg.isSuccess()) { 
			/**
			 * 成功返回json对象字符串，data数据如下：
			 * {
				    "uid": "1123344567",
				    "status": "success",
				    "respCode": "0",
				    "respMsg": "提交成功！",
				    "totalChargeNum": 3,
				    "result": [
				        {
				            "msgid": "59106412291510790879",
				            "mobile": "13489080110",
				            "code": "0",
				            "msg": "提交成功.",
				            "chargeNum": 1
				        },
				        {
				            "msgid": "59106412291510790880",
				            "mobile": "13489080111",
				            "code": "0",
				            "msg": "提交成功.",
				            "chargeNum": 1
				        },
				        {
				            "msgid": "59106412291510790881",
				            "mobile": "13489080112",
				            "code": "0",
				            "msg": "提交成功.",
				            "chargeNum": 1
				        }
				    ]
				}
			 */
			System.out.println(resultMsg.getData());
		} else {
			/**
			 *  1000：服务器出现未知异常！
			 *  1001 操作不合法，操作前未获取Token，或Token已过时
			 *	1002 签名验证不通过！
			 *	1003 Json参数解析出错
			 *	1004 操作不合法，cust_code: xxxxxx不存在
			 *	1005 客户端IP鉴权不通过
			 *	1006 客户账号已停用！
			 *	1008 客户提交接口协议HTTPS, 与客户参数允许的协议不一致！
			 *	1009 提交的短信内容超过规定的长度！
			 *	1011 客户账户不存在！
			 *	1012 账户没有足够的余额
			 *	1013 扩展号码(sp_code)不符合规范！
			 */
			System.out.println(resultMsg.getCode());
			System.out.println(resultMsg.getMsg());
		}
	}
}
