package spring_boot_send_email;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;


@RestController
    public class EmailController {
        @RequestMapping(value = "/sendemail")
        public String sendEmail() {
            try {
                sendmail();
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
            return "Email sent successfully";
        }
        private void sendmail() throws AddressException, MessagingException, IOException {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("jamesmuthiani981@gmail.com", false));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jamesmuthiani981@gmail.com"));
            msg.setSubject("Test Message");
            msg.setContent("james rack email", "text/html");
            msg.setSentDate(new Date());

            Multipart multipart = getMultipart();
            msg.setContent(multipart);
            Transport.send(msg);
        }

    private static Multipart getMultipart() throws MessagingException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Tutorials point email", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        try {
            attachPart.attachFile("image.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        multipart.addBodyPart(attachPart);
        return multipart;
    }
}
