package org.automotive.logging;

import static org.automotive.constants.StringConstants.BASE_PACKAGE_NAME;
import static org.automotive.constants.StringConstants.GMAIL_SMTP_HOST;
import static org.automotive.constants.StringConstants.SMTPS_PROTOCOL;
import static org.automotive.constants.StringConstants.SMTP_SSL_PORT;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.SmtpAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.beans.factory.annotation.Value;

public class AdditionalLoggerConfiguration extends XmlConfiguration {

  private static final String APPENDER_NAME = "email appender";
  private static final String EMAIL_SUBJECT = "Cars scraper errors";
  
  @Value("${automotive.processes.username}")
  private String automotiveProcessesUsername;
  @Value("${automotive.processes.password}")
  private String automotiveProcessesPassword;
  @Value("${email.errors.to}")
  private String emailErrorsTo;  
  @Value("${email.errors.from}")
  private String emailErrorsFrom;

  public AdditionalLoggerConfiguration(
      final LoggerContext loggerContext, final ConfigurationSource configSource) {
    super(loggerContext, configSource);
  }

  @Override
  protected void doConfigure() {
    super.doConfigure();
    final LoggerContext context = (LoggerContext) LogManager.getContext(false);
    final Configuration config = context.getConfiguration();
    final Layout<String> layout = PatternLayout.newBuilder()
            .withConfiguration(config)
            .withPattern(
                    "%d{yyyy-MM-dd HH:mm:ss} [%tid] [%-5level] %c{1} - %m%n")
            .build();

    final Appender appender =
        new SmtpAppender.Builder()
            .setConfiguration(config)
            .setName(APPENDER_NAME)
            .setTo(emailErrorsTo)
            .setFrom(emailErrorsFrom)
            .setSubject(EMAIL_SUBJECT)
            .setSmtpHost(GMAIL_SMTP_HOST)
            .setSmtpProtocol(SMTPS_PROTOCOL)
            .setSmtpPort(Integer.parseInt(SMTP_SSL_PORT))
            .setSmtpUsername(automotiveProcessesUsername)
            .setSmtpPassword(automotiveProcessesPassword)
            .setLayout(layout)
            .build();

    appender.start();
    addAppender(appender);
    LoggerConfig loggerConfig =
        LoggerConfig.createLogger(
            true,
            Level.ERROR,
                BASE_PACKAGE_NAME,
                BASE_PACKAGE_NAME,
            new AppenderRef[] {AppenderRef.createAppenderRef(APPENDER_NAME, Level.ERROR, null)},
            null,
            config,
            null);
    loggerConfig.addAppender(appender, Level.ERROR, null);
    addLogger(loggerConfig.getName(), loggerConfig);
  }
}
