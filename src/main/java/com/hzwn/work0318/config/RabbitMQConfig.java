package com.hzwn.work0318.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig implements InitializingBean{

    @Autowired
    RabbitTemplate rabbitTemplate;

    //该方法在类创建完成并且属性全部设置完成之后调用
    @Override
    public void afterPropertiesSet() throws Exception {

        //指定 rabbitTemplate 的MQServer是否正常收到消息的回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("correlationData "+correlationData);
                System.out.println("ack 消息有没有正常到达MQServer "+ack);
                System.out.println("cause ack为false时代表mq没有正常拿到消息的原因 "+cause);
            }
        });

        //指定rabbitTemplate 无法正常到达队列的消息的强制退回
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("message "+message);
                System.out.println("replyCode "+replyCode);
                System.out.println("replyText "+replyText);
                System.out.println("exchange "+exchange);
                System.out.println("routingKey "+routingKey);
            }
        });
        rabbitTemplate.setMandatory(true);  //补充设置

    }
}
