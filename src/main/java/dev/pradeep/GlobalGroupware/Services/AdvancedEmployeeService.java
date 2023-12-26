package dev.pradeep.GlobalGroupware.Services;

import dev.pradeep.GlobalGroupware.Entity.EmailDetails;
import dev.pradeep.GlobalGroupware.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AdvancedEmployeeService {

    @Autowired private JavaMailSender javaMailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendMail(EmailDetails email){
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            //setting up
            message.setFrom(sender);
            message.setTo(email.getRecipient());
            message.setText(email.getMsgBody());
            message.setSubject(email.getSubject());

            //sending mail
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        }  catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
