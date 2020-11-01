package io.renren.modules.app.utils;

import com.google.gson.JsonObject;
import io.renren.modules.app.sms.util.HttpsClient;

public class WMYUtils {
    final static String server_url = "https://dopen.weimob.com/fuwu/b/oauth2/";
    final static String client_id = "D87AF3CFFDF09908E1F49BFC925A0E02";
    final static String client_secret = "7DFAFB5373B44FCE7CFC841E7BDD032E";
    final static String redirect_uri = "";
    final static String state = "";


//    static String authorize(){
//        JsonObject jsonObject=new JsonObject();
//        String postData= jsonObject.toString();
//        String resp = HttpsClient.post("https://my.wlwx.com:6016" + "/req/api/server/Sms/smsSend",
//                postData, "application/json", "utf-8");
//    }
}
