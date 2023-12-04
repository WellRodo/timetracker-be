package miniapp.timetracker.service;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{
    @Autowired
    private JavaMailSender mailSender;

    public String sendSimpleMessage(final String to){
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
        SimpleMailMessage message = new SimpleMailMessage();
        String pass = randomStringGenerator.generate(15);
        message.setText("Пароль для вашей учетной записи: " + pass);
        message.setTo(to);
        message.setSubject("Временный пароль");
        message.setFrom("timetrackerastu@gmail.com");
        mailSender.send(message);
        return pass;
    }
}
