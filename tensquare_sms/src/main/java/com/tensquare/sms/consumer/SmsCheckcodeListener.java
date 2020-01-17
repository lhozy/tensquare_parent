package com.tensquare.sms.consumer;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 手机短信验证码的mq监听
 */
@Component
@RabbitListener(queues = "sms.checkcode")
public class SmsCheckcodeListener {
    //方法1：直接注入自定义的常量
    //签名
    @Value("${aliyun.sms.signName}")
    private String signName;
    //模版编号
    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    //方法2：注入环境对象，可用户获取环境常量
    @Autowired
    private Environment environment;

    /**
     * 处理短信验证码
     */
    @RabbitHandler
    public void handleSmsCheckcode(Map<String,String> message){
        System.out.println("手机号："+message.get("mobile"));
        System.out.println("验证码："+message.get("checkcode"));

        //准备发送短信需要的参数
        String templateParam="{\"checkcode\":"+message.get("checkcode")+"}";
        String outId="123123";

        //通过环境对象获取自定义常量
        String accessKeyId=environment.getProperty("aliyun.sms.accessKeyId");
        String accessKeySecret=environment.getProperty("aliyun.sms.accessKeySecret");

        //发送短信
        try {
            SmsUtils.sendSms(message.get("mobile"), signName, templateCode, templateParam, outId, accessKeyId, accessKeySecret);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
