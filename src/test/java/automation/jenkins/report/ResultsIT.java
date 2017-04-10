package automation.jenkins.report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Properties;
import java.util.TimeZone;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ResultsIT {

	String today;
	String host = "smtp.gmail.com";
	String from = "automation.resultsqait@gmail.com";
	String password = "QaitAutomation";
	int port = 465;

	String emailTo[] = { "Abhishek.Ranjan@contractor.cengage.com", "Steffin.Spears@cengage.com" };
	String emailCC[] = { "HarshSehgal@qainfotech.com", "AlokKumar@qainfotech.com" };

	public void sendResultsMail(String email_text, String projectName) throws MessagingException, IOException {
		if (true) {
			TimeZone.setDefault(TimeZone.getTimeZone("IST"));
			SimpleDateFormat f = new SimpleDateFormat("dd MMM, EEE | HH:mm:ss z yyyy");
			today = f.format(new Date());
			Message message = new MimeMessage(getSession());
			message.addFrom(new InternetAddress[] { (new InternetAddress(from)) });
			setMailRecipient(message);
			message.setSubject(setMailSubject(projectName));
			message.setContent(setBodyText(email_text));
			Session session = getSession();
			Transport transport = session.getTransport("smtps");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		System.out.println("Result report has been emailed");
	}

	private Session getSession() {
		Authenticator authenticator = new Authenticator(from, password);
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtps");
		properties.put("mail.smtps.auth", "true");
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", String.valueOf(port));
		return Session.getInstance(properties, authenticator);
	}

	String mailtext = "";
	MimeBodyPart messageBodyPart = null;

	private Multipart setBodyText(String mail_data) throws MessagingException, IOException {
		messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setContent(mail_data, "text/html");
		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// addAttachment(multipart, messageBodyPart, "analytics.html");

		return multipart;
	}

	private String setMailSubject(String projectName) {
		return (projectName + " | " + today);
	}

	private void setMailRecipient(Message message) throws AddressException, MessagingException, IOException {
		@SuppressWarnings("unused")
		String list = System.getProperty("mailingList", "onsite");

		for (String toRecipient : emailTo) {
			System.out.println("Email Ids of To Recipient:- " + toRecipient);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toRecipient));
		}
		for (String CcRecipient : emailCC) {
			System.out.println("Email Ids of CC Recipient:- " + CcRecipient);
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(CcRecipient));
		}
	}

	@SuppressWarnings("unused")
	private static void addAttachment(Multipart multipart, MimeBodyPart messageBodyPart, String filepath)
			throws MessagingException {
		messageBodyPart = new MimeBodyPart();
		File f = new File(filepath);
		DataSource source = new FileDataSource(f);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(f.getName());
		multipart.addBodyPart(messageBodyPart);
	}

	class Authenticator extends javax.mail.Authenticator {
		private final PasswordAuthentication authentication;

		public Authenticator(String username, String password) {
			authentication = new PasswordAuthentication(username, password);
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
}
