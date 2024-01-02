package cu.edu.cujae.backend.core.util;


import java.io.IOException;
import java.lang.module.Configuration;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Template;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


@Service
public class EmailSenderService {
   
	@Autowired
    private JavaMailSender emailSender;
    
	@Autowired
    private Configuration freemarkerConfig;

    
    
}