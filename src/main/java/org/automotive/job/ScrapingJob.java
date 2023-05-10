package org.automotive.job;

import static java.util.Collections.singletonList;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.automotive.executor.SingleThreadScrapingExecutor;
import org.automotive.notifications.email.EmailSeder;
import org.automotive.notifications.email.javabean.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class ScrapingJob {

  @Autowired private SingleThreadScrapingExecutor singleThreadScrapingExecutor;

  @Autowired private EmailSeder emailSeder;

  @Value("${email.errors.to}")
  private String resultSendTo;

  public void doJob() {
    log.info("Staring scraping...");
    String scrapingResults = singleThreadScrapingExecutor.execute();
    log.info("Scraping terminated");
    emailSeder.sendEmail(buildEmailWithResults(scrapingResults));
    log.info("Scraping results: " + scrapingResults);
  }

  private EmailDetails buildEmailWithResults(String emailText) {
    return EmailDetails.builder()
        .to(singletonList(resultSendTo))
        .subject("Scraping results")
        .text(emailText)
        .build();
  }
}
