package com.tensquare.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "queue_ts3")
public class Receiver {
    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("消费者3："+msg);
    }
}
