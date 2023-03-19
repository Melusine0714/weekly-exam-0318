package com.hzwn.work0318.consumers;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
public class SendEmailConsumer {

    @Autowired
    JavaMailSender javaMailSender;

    @RabbitListener(queues = "sendEmailQueue")
    public void onMsg(Message message, Map<String,String> infoMap, Channel channel) throws IOException {

        try {
            System.out.println(infoMap);
            System.out.println(message);

            System.out.println("接收发送邮件的需求 " + new Date());
            sendEmail(infoMap.get("email"), infoMap.get("subject"), infoMap.get("text"));
            System.out.println("邮件发送成功 " + new Date());

        }catch (Exception e){
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            return;
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }



    private void sendEmail(String toEmail,String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2512835516@qq.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }




}
