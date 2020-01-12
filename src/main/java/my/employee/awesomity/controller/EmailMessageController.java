package my.employee.awesomity.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import my.employee.awesomity.model.EmailMessage;
import my.employee.awesomity.service.EmailMessageService;

/**
 * EmailMessageController
 */
@RestController
public class EmailMessageController {

    @Autowired
    EmailMessageService service;
    
    @RequestMapping(value="/send", method=RequestMethod.POST)
	public String sendEmail(@RequestBody EmailMessage emailmessage) throws AddressException, MessagingException, IOException {
		service.sendmail(emailmessage);
		return "Email sent successfully";
	}
    
}