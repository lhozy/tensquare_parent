package com.tensquare.rabbitmq.test;

import com.tensquare.rabbitmq.RabbitmqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class RabbitmqAppTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send(){
        //直接模式 direct
        rabbitTemplate.convertAndSend("queue_ts1","直接模式direct");
    }
    @Test
    public void sendFanout(){
        //分列模式 fanout
        rabbitTemplate.convertAndSend("ex_fanout","","分列模式fanout");
    }
    @Test
    public void sendTopic(){
        //主题模式 topic
        //rabbitTemplate.convertAndSend("ex_topic","good.abc","主题模式消费1个");
        rabbitTemplate.convertAndSend("ex_topic","good.abc.log","主题模式消费2个");
        //rabbitTemplate.convertAndSend("ex_topic","good.log","主题模式消费3个");
    }
}
