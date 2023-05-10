package org.automotive.notifications.email;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;
import static org.automotive.constants.StringConstants.GMAIL_SMTP_HOST;
import static org.automotive.constants.StringConstants.MAIL_SMTP_AUTH;
import static org.automotive.constants.StringConstants.MAIL_SMTP_HOST;
import static org.automotive.constants.StringConstants.MAIL_SMTP_PORT;
import static org.automotive.constants.StringConstants.MAIL_SMTP_SSL_ENABLE;
import static org.automotive.constants.StringConstants.SMTP_TLS_PORT;

import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.automotive.exception.TerminateProcessException;
import org.automotive.notifications.email.javabean.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailSeder {

  @Value("${email.errors.from}")
  private String fromEmail;
  @Value("${email.errors.from}")
  private String userName;
  @Value("${automotive.processes.password}")
  private String password;

  public void sendEmail(EmailDetails emailDetails) {
    try {
      Message message = new MimeMessage(initSession());
      message.setFrom(new InternetAddress(fromEmail));
      message.addRecipients(
          Message.RecipientType.TO, getRecipients(emailDetails, EmailDetails::getTo));
      message.addRecipients(
          Message.RecipientType.CC, getRecipients(emailDetails, EmailDetails::getCc));
      message.addRecipients(
          Message.RecipientType.BCC, getRecipients(emailDetails, EmailDetails::getBcc));
      message.setSubject(emailDetails.getSubject());
      message.setText(emailDetails.getText());
      Transport.send(message);
      log.info("Email Message Sent Successfully");
    } catch (MessagingException e) {
      log.error(
          "Exception occurred during sending email to: {}. Exception: ", emailDetails.getTo(), e);
    }
  }

  private Session initSession() {
    Properties properties = new Properties();
    properties.put(MAIL_SMTP_HOST, GMAIL_SMTP_HOST);
    properties.put(MAIL_SMTP_PORT, SMTP_TLS_PORT);
    properties.put(MAIL_SMTP_SSL_ENABLE, TRUE.toString());
    properties.put(MAIL_SMTP_AUTH, TRUE.toString());
    return Session.getInstance(
        properties,
        new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
          }
        });
  }

  private Address[] getRecipients(
      EmailDetails emailDetails, Function<EmailDetails, List<String>> functionToGetRecipients) {
    if (nonNull(functionToGetRecipients.apply(emailDetails))) {
      return functionToGetRecipients.apply(emailDetails).stream()
          .map(this::stringToInternetAddress)
          .toArray(Address[]::new);
    }
    return new Address[0];
  }

  Address stringToInternetAddress(String address) {
    try {
      return new InternetAddress(address);
    } catch (Exception e) {
      log.error(
          "Exception occurred during converting String to internet address ({}). Exception: ",
          address,
          e);
      throw new TerminateProcessException(
          "Exception occurred during converting String to internet address: " + address, e);
    }
  }
}
