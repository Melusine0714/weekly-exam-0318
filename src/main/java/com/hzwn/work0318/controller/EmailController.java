package com.hzwn.work0318.controller;

import com.hzwn.work0318.pojo.Email;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private void sendEmail(String toEmail,String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2512835516@qq.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        try {
            TimeUnit.SECONDS.sleep(30);  //模拟邮箱服务器高延时
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(message);
    }
    Email email = new Email();
    @RequestMapping("getEmailCode")
    public String getEmailCode(String email,String title,String content){
        System.out.println(email);
        System.out.println(title);
        System.out.println(content);
        //使用正则表达式判断邮箱地址是否正确
        String format = "[1-9]\\d{7,10}@qq\\.com";
        if (email==null || !email.matches(format)){
            return "邮箱地址非法";
        }
        //发送邮件

        Map<String,String> infoMap = new HashMap<>();
        infoMap.put("email",email);
        infoMap.put("subject",title);
        infoMap.put("text",content);
        rabbitTemplate.convertAndSend("sendEmailExchange","sendEmail",infoMap);
        System.out.println("邮件已发送");
        return "ok";
    }
}
