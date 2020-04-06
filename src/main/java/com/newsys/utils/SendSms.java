package com.newsys.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

public class SendSms {
    private static final Integer appid = 1400343636;
    private static final String appkey = "3c692b46e2ea155746c1ab8353b8a1db";
    private static final String smsSign = "sysir网站";
    private static final Integer templateId = 568762;

    public static String getTelResult(String tel) {
        String telCode = "";
        SmsSingleSenderResult result = new SmsSingleSenderResult();
        try {
            telCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            String[] params = {telCode};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            result = ssender.sendWithParam("86", tel,
                    templateId, params, smsSign, "", "");

        } catch (HTTPException | JSONException | IOException e) {
            e.printStackTrace();
        }
        if (result.result == 0) {
            return telCode;
        } else {
            return "500";
        }
    }
}
