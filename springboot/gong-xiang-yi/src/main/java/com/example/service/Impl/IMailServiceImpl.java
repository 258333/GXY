package com.example.service.Impl;

import com.example.mapper.UserMapper;
import com.example.pojo.Result;
import com.example.service.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class IMailServiceImpl implements IMailService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUserName;

    @Override
    public Result getCode(String username, String email) {
        // 非空校验
        if (null == username || "".equals(username)) return Result.error("账号不能为空！");
        if (null == email || "".equals(email)) return Result.error("邮箱不能为空！");

//        System.out.println(staffNumber);
//        System.out.println(mailAddress);
        // 账号存在校验
        String email_user = userMapper.getEmailByUsername(username);
//        System.out.println(email);
        if (email_user == null) return Result.error("邮箱不存在！");
        if (!email_user.equals(email)) return Result.error("邮箱不匹配！");


        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String verifyCode = operations.get("verify");
        // 使用当前时间作为种子值
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
//        if (verifyCode == null) {
        verifyCode = String.valueOf(random.nextInt(899999) + 100000);//生成短信验证码
//        }
        // 验证码存入redis并设置过期时间
        operations.set("verify", verifyCode, 5, TimeUnit.MINUTES);

        // 编写邮箱内容
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><title></title></head><body>");
        stringBuilder.append("您好<br/>");
        stringBuilder.append("您的验证码是：").append(verifyCode).append("<br/>");
        stringBuilder.append("您可以复制此验证码并返回至共享益找回密码页面，以验证您的邮箱。<br/>");
        stringBuilder.append("此验证码只能使用一次，在");
//        stringBuilder.append(overtime.toString());
        stringBuilder.append("5分钟内有效。验证成功则自动失效。<br/>");
        stringBuilder.append("如果您没有进行上述操作，请忽略此邮件。");
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // 发件配置并发送邮件
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            //这里只是设置username 并没有设置host和password，因为host和password在springboot启动创建JavaMailSender实例的时候已经读取了
            mimeMessageHelper.setFrom(mailUserName);
            // 用户的邮箱地址
            mimeMessageHelper.setTo(email);
            // 邮件的标题
            mimeMessage.setSubject("邮箱验证-共享益");
            // 上面所拼接的邮件内容
            mimeMessageHelper.setText(stringBuilder.toString(), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Result.success("获取验证码成功，请查看移步您的邮箱" + email + "查看验证码！");
    }
}
