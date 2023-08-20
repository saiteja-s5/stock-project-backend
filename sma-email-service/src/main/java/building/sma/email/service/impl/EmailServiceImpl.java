package building.sma.email.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import building.sma.email.exception.EmailNotSentException;
import building.sma.email.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmailWithExcelAttachment(String from, String to, String subject, String body, String attachmentName,
	    byte[] attachmentData) {
	log.debug(String.format("Mail with attachment '%s' initiated to '%s' from '%s'", attachmentName, to, from));
	MimeMessage message = mailSender.createMimeMessage();
	try {
	    MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
	    messageHelper.setFrom(from);
	    messageHelper.setTo(to);
	    messageHelper.setSubject(subject);
	    messageHelper.setText(body);
	    ByteArrayDataSource dataSource = new ByteArrayDataSource(attachmentData,
		    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    messageHelper.addAttachment(attachmentName, dataSource);
	    mailSender.send(message);
	    log.info(String.format("Mail sent successfully to '%s' from '%s'", to, from));
	} catch (Exception e) {
	    log.warn(String.format("Mail not sent to '%s' from '%s'", to, from));
	    log.error("Unable to send email, an exception occured", e);
	    throw new EmailNotSentException(e.getMessage());
	}
    }

}
