package building.sma.email.service;

public interface EmailService {

    void sendEmailWithExcelAttachment(String from, String to, String subject, String body, String attachmentName,
	    byte[] attachmentData);

}
