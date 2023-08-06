package building.stockapp.service;

public interface EmailService {

	void sendReportEmail(String from, String to, String subject, String body, String attachmentName,
			byte[] attachmentData);

}
