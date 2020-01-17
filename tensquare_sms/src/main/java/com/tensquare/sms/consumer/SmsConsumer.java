package com.tensquare.sms.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author bruce
 * @date 2020/1/17 0017 - 下午 10:23
 */
@Component
@RabbitListener(queues = "sms")
public class SmsConsumer {

    @RabbitHandler
    public void executeSms(Map<String,String> map){

        map.forEach((k,v)->{
            System.out.println("---发送验证码:"+v+"给"+k);
        });

    }

}
