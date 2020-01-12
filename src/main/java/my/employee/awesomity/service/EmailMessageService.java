package my.employee.awesomity.service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import my.employee.awesomity.model.EmailMessage;

/**
 * EmailMessageService
 */
@Service
public class EmailMessageService {

    @Value("${gmail.username}")
    private String username;
    @Value("${gmail.password}")
    private String password;

    public void sendmail(EmailMessage emailmessage) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailmessage.getToAddress()));
		msg.setSubject("You have been registered");
		msg.setContent("Welcome", "text/html");
        msg.setSentDate(new Date());
        
        // sends the e-mail
		Transport.send(msg);
    }

}