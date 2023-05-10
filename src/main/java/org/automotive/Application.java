package org.automotive;

import lombok.extern.slf4j.Slf4j;
import org.automotive.configuration.ApplicationConfiguration;
import org.automotive.job.ScrapingJob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
public class Application {

  public static void main(String[] args) {
    log.info("Started at: {}", System.nanoTime());
    ApplicationContext applicationContext =
        new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    Environment environment = applicationContext.getBean(Environment.class);
    log.info("Scraping {} pages...", environment.getProperty("auto.ria.pages.to.scrape"));
    applicationContext.getBean(ScrapingJob.class).doJob();
    log.info("Ended at: {}", System.nanoTime());
  }
}
