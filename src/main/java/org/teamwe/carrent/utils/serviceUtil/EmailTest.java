package org.teamwe.carrent.utils.serviceUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailTest {

    private static String host = null;
    private static String user = null;
    private static String password = null;

    @Autowired
    public EmailTest(@Value("${emailServer.serverhost}") String host,@Value("${emailServer.user}") String user,@Value("${emailServer.password}" ) String password ) {

        EmailTest.host = host;
        EmailTest.user = user;
        EmailTest.password = password;
    }

    public static boolean sendMail(String strMail, String strTitle, String strText){
        boolean bret = false;
        try
        {
            final Properties props = new Properties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", host);
            //你自己的邮箱
            props.put("mail.user", user);
            //你开启pop3/smtp时的验证码
            props.put("mail.password", password);
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.starttls.enable", "true");

            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            InternetAddress to = new InternetAddress(strMail);
            message.setRecipient(RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject(strTitle);

            // 设置邮件的内容体
            message.setContent(strText, "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
            bret = true;
        }
        catch (AddressException e) {
            e.printStackTrace();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bret;
    }

    public static void main(String[] args) {
        if (sendMail("1553741667@qq.com", "测试QQ邮箱发送", "<body><p>你们好吗</p></body>"))
            System.out.println("QQ邮件发送成功");

//        if (send_163mail("xxxxxx@163.com", "测试网易邮箱发送", "<body><p>你们好吗</p></body>"))
//            System.out.println("网易邮件发送成功");
    }
}
