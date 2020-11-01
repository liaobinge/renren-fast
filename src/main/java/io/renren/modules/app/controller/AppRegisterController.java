/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;


import com.alibaba.fastjson.JSON;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.form.AppRegisterForm;
import io.renren.modules.app.form.AuLoginForm;
import io.renren.modules.app.sms.client.SmsClient;
import io.renren.modules.app.sms.util.HttpsClient;
import io.renren.modules.app.sms.util.MD5;
import io.renren.modules.app.sms.util.Utils;
import io.renren.modules.app.sms.vo.ResultMsg;
import io.renren.modules.app.sms.vo.SmsReq;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.generator.entity.TAppAuthorizeEntity;
import io.renren.modules.generator.entity.TAppSmsEntity;
import io.renren.modules.app.form.SmsCodeForm;
import io.renren.modules.generator.entity.TAppUserEntity;
import io.renren.modules.generator.service.TAppAuthorizeService;
import io.renren.modules.generator.service.TAppSmsService;
import io.renren.modules.generator.service.TAppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 注册
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP注册接口")
public class AppRegisterController {
    private final String AU_ALIPAY = "Alipay";
    private final String AU_WECHAT = "Wechat";
    private final String AU_QQ = "QQ";
    private final String AU_WEIBO = "Weibo";
    private final String AU_ONEKEY="OneKey";

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private TAppSmsService smsCodeService;
    @Autowired
    private TAppUserService appUserService;

    @Autowired
    private TAppSmsService smsService;

    @Autowired
    private TAppAuthorizeService appAuthorizeService;

    @PostMapping("appRegister")
    @ApiOperation("注册")
    public R register(@RequestBody AppRegisterForm form) {

        //表单校验
        ValidatorUtils.validateEntity(form);
        TAppUserEntity user = appUserService.queryByMobile(form.getMobile());

        TAppSmsEntity codeEntity = smsCodeService.queryByMobile(form.getMobile());
        if(codeEntity==null){
            return R.error("未查询到验证码");
        }
        if ((System.currentTimeMillis() - codeEntity.getSendTime().getTime()) > 10 * 60 * 1000) {
            return R.error("验证码已过期");
        }
        if (codeEntity.getQueryCount() > 5) {
            return R.error("验证码已失效（输入错误超过五次）");
        }
        if (!form.getCode().equals(codeEntity.getSmsCode())) {
            codeEntity.setQueryCount(codeEntity.getQueryCount() + 1);
            smsCodeService.updateById(codeEntity);
            return R.error("验证码错误");
        } else {
            if (user != null) {
                //生成token
                String token = jwtUtils.generateToken(user.getUserId());
                Map<String, Object> map = new HashMap<>();
                map.put("token", token);
                map.put("expire", jwtUtils.getExpire());
                map.put("user", user);
                return R.ok().put("data", map);
            }
            user = new TAppUserEntity();
            user.setMobile(form.getMobile());
            user.setUserName("游客" + "_" + form.getMobile().substring(7));
            user.setCreateTime(new Date());

            user.setSex(0);
            user.setHeadUrl(" ");
            user.setAddress(" ");
            appUserService.save(user);

        }

        String token = jwtUtils.generateToken(user.getUserId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        map.put("user", user);
        return R.ok().put("data", map);
    }

    @PostMapping("sendCode")
    @ApiOperation("发送短信")
    public R sendCode(@RequestBody SmsCodeForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        TAppSmsEntity smsEntity = smsCodeService.queryByMobile(form.getMobile());
        if ((System.currentTimeMillis() - smsEntity.getSendTime().getTime())< 5 * 60 * 1000) {
            return R.error("两分钟内已发过验证码");
        }
        TAppSmsEntity codeEntity = new TAppSmsEntity();
//        boolean isSended = true;
//        if (codeEntity == null) {
//            codeEntity = new SmsCodeEntity();
//            isSended = false;
//        }
        codeEntity.setMobile(form.getMobile());
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        String content="模板[验证码]";
        codeEntity.setSmsCode(code);
        codeEntity.setSendContent(content);
        codeEntity.setSmsName(form.getName());
        codeEntity.setSmsProduct(form.getProduct());
        codeEntity.setQueryCount(0);
        codeEntity.setSendTime(new Date());
        codeEntity.setSmsUse(form.getSmsUse());
        try {
          String resp=  sendSmsCode(codeEntity);
          JSONObject result= new JSONObject(resp);
          if(result.getString("code").equals("00000")){
              smsCodeService.save(codeEntity);
              return R.ok().put("data",codeEntity);
          }else{
              return R.error("验证码发送错误"+result.getString("code"));
          }
        }catch (Exception e){
            return R.error("验证码发送错误,"+e.getMessage());
        }


       // return R.ok().put("data", codeEntity);
    }
    private ResultMsg sendSms(TAppSmsEntity codeEntity){
        try {
            String custCode = "17338852882";							 //[必填] 用户账号
            String password = "bm9c123123";						 //[必填] 账号密码
            String serviceBaseUrl = "https://my.wlwx.com:6016"; 			 //[必填] https://ip:port

            /**
             * 1.通过SmsReq对象传参
             */

            SmsReq smsReq = new SmsReq();
            smsReq.setUid("");							//[选填] 业务标识，由贵司自定义32为数字透传至我司
            smsReq.setCust_code(custCode);				//[必填] 用户账号
            smsReq.setContent(codeEntity.getSendContent());				//[必填] 短信内容
            smsReq.setDestMobiles(codeEntity.getMobile());		//[必填] 接收号码，同时发送给多个号码时,号码之间用英文半角逗号分隔(,)
            smsReq.setNeed_report("yes");				//[选填] 状态报告需求与否，是 yes 否 no 默认yes
            smsReq.setSp_code("");						//[选填] 长号码
            smsReq.setMsgFmt("8");						//[选填] 信息格式，0：ASCII串；3：短信写卡操作；4：二进制信息；8：UCS2编码；默认8

            SmsClient smsClient = new SmsClient();
            ResultMsg resultMsg = smsClient.sendSms(smsReq, password, serviceBaseUrl);
            return resultMsg;


        } catch (Exception e) {
            return null;
        }

    }

    @PostMapping("auLogin")
    @ApiOperation("授权登录")
    public R auLogin(@RequestBody AuLoginForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        TAppAuthorizeEntity appAuthorizeEntity = null;
        if (form.getType().equals(AU_ALIPAY)) {
            appAuthorizeEntity = appAuthorizeService.queryByAlipayAu(form.getAuToken());

        } else if (form.getType().equals(AU_WECHAT)) {
            appAuthorizeEntity = appAuthorizeService.queryByWechatAu(form.getAuToken());

        } else if (form.getType().equals(AU_QQ)) {
            appAuthorizeEntity = appAuthorizeService.queryByQQAu(form.getAuToken());

        } else if (form.getType().equals(AU_WEIBO)) {
            appAuthorizeEntity = appAuthorizeService.queryByWeiboAu(form.getAuToken());

        }else if (form.getType().equals(AU_ONEKEY)) {
            appAuthorizeEntity = appAuthorizeService.queryByOneKeyAu(form.getAuToken());
        }
        if (appAuthorizeEntity == null) {
            return R.error("该授权未绑定用户");
        }
        TAppUserEntity user = appUserService.getById(appAuthorizeEntity.getUserId());
        String token = jwtUtils.generateToken(user.getUserId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        map.put("user", user);
        return R.ok().put("data", map);
    }


    @PostMapping("oneKeyLogin")
    @ApiOperation("一键登录")
    public R oneKeyLogin(@RequestParam("oneKeyToken") String onKeyToken){
        String tel="";
        try {
            tel=getTel(onKeyToken);
            TAppUserEntity user=appUserService.queryByMobile(tel);
            if(user==null){
                user = new TAppUserEntity();
                user.setMobile(tel);
                user.setUserName("游客" + "_" + tel.substring(7));
                user.setCreateTime(new Date());

                user.setSex(0);
                user.setHeadUrl(" ");
                user.setAddress(" ");
                appUserService.save(user);

            }

            String token = jwtUtils.generateToken(user.getUserId());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            map.put("user", user);
            return R.ok().put("data", map);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        //return R.error("一键登录失败");
    }
    @Login
    @PostMapping("bindAu")
    public R bindAu(@RequestAttribute("userId") Long userId, @RequestBody AuLoginForm form) {
        ValidatorUtils.validateEntity(form);
        TAppAuthorizeEntity appAuthorizeEntity = null;
        if (form.getType().equals(AU_ALIPAY)) {
            appAuthorizeEntity = appAuthorizeService.queryByAlipayAu(form.getAuToken());

        } else if (form.getType().equals(AU_WECHAT)) {
            appAuthorizeEntity = appAuthorizeService.queryByWechatAu(form.getAuToken());

        } else if (form.getType().equals(AU_QQ)) {
            appAuthorizeEntity = appAuthorizeService.queryByQQAu(form.getAuToken());

        } else if (form.getType().equals(AU_WEIBO)) {
            appAuthorizeEntity = appAuthorizeService.queryByWeiboAu(form.getAuToken());

        }
        if (appAuthorizeEntity != null) {
            return R.error("无法重复绑定");
        } else {
            appAuthorizeEntity = new TAppAuthorizeEntity();
            if (form.getType().equals(AU_ALIPAY)) {
//                appAuthorizeEntity.setA(form.getAuToken());
//                appAuthorizeEntity.setAlipayAuTime(new Date());

            } else if (form.getType().equals(AU_WECHAT)) {
                appAuthorizeEntity.setWechatAu(form.getAuToken());
               // appAuthorizeEntity.setWechatAuTime(new Date());

            } else if (form.getType().equals(AU_QQ)) {
                appAuthorizeEntity.setQqAu(form.getAuToken());
              //  appAuthorizeEntity.setQqAuTime(new Date());

            } else if (form.getType().equals(AU_WEIBO)) {
                appAuthorizeEntity.setWeiboAu(form.getAuToken());
               // appAuthorizeEntity.setWeiboAuTime(new Date());

            }
        }
        appAuthorizeEntity.setUserId(userId);
        appAuthorizeEntity.setCreateTime(new Date());
        appAuthorizeService.save(appAuthorizeEntity);

        return R.ok().put("data", appAuthorizeEntity);
    }
    private String getTel(String token) throws  Exception{
        String APP_ID = "056AC508AA17706448D7C7517DF3A0E7";
        String APP_SECRET = "1388D66076F99E9254B02157D1EA185C";
        String MASTER_SECRET = "616CADF30A8F273EF08D46706D9B441C";
        JSONObject jsonObject= new JSONObject();
        long current_time = System.currentTimeMillis();
        String strAppSign = Utils.makeMD5Lower32(MASTER_SECRET + current_time);
        jsonObject.put("app_id", APP_ID);         // 应用ID
        jsonObject.put("sign", strAppSign);                        // 应用签名 md5(api_master_secret + current_time);
        jsonObject.put("time_stamp", current_time);                  // 当前时间
        jsonObject.put("request_id", System.currentTimeMillis());      // 用户端的请求序列号
        jsonObject.put("access_token", token);             // access_token
        String postData= jsonObject.toString();
        String resp = HttpsClient.post("https://my.wlwx.com:6016" + "/req/api/server/Onekey/mobileQuery",
                postData, "application/json", "utf-8");
        JSONObject result = new JSONObject(resp);
        String strCode = result.getString("code");
        if ("00000".equals(strCode)) {
            JSONObject objJson = result.getJSONObject("object");
            objJson.put("title", "一键验证");
            objJson.put("code", "00000");
            // 获取手机号信息成功
            String encodeTel = objJson.getString("tel");
            return encodeTel;
        } else {
           // toast(OxSdkCode.msgByCode(strCode, "响应码：" + strCode));
        }
        return  "";
    }
    private String sendSmsCode(TAppSmsEntity codeEntity) throws  Exception{
        String APP_ID = "056AC508AA17706448D7C7517DF3A0E7";
        String APP_SECRET = "1388D66076F99E9254B02157D1EA185C";
        String MASTER_SECRET = "616CADF30A8F273EF08D46706D9B441C";
        JSONObject jsonObject= new JSONObject();
        String current_time =String.valueOf(System.currentTimeMillis()) ;
        //String strAppSign = MD5.getMD5((MASTER_SECRET + current_time).getBytes());
        String strAppSign = Utils.makeMD5Lower32(MASTER_SECRET + current_time);
        jsonObject.put("app_id", APP_ID);         // 应用ID
        jsonObject.put("sign", strAppSign);
        jsonObject.put("tel", codeEntity.getMobile());// 应用签名 md5(api_master_secret + current_time);
        jsonObject.put("time_stamp13", current_time);
        // 当前时间
        JSONObject sms_param=new JSONObject();
        sms_param.put("code",codeEntity.getSmsCode());
        sms_param.put("name",codeEntity.getSmsName());
        sms_param.put("product",codeEntity.getSmsProduct());
        jsonObject.put("sms_template","验证码");
        jsonObject.put("sms_param",sms_param);
       // jsonObject.put("request_id", System.currentTimeMillis());      // 用户端的请求序列号
       // jsonObject.put("access_token", token);             // access_token
        String postData= jsonObject.toString();
        String resp = HttpsClient.post("https://my.wlwx.com:6016" + "/req/api/server/Sms/smsSend",
                postData, "application/json", "utf-8");

        return  resp;
    }
}
