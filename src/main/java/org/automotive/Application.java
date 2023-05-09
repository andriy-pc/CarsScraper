package org.automotive;

import static org.automotive.constants.EnvVarNames.AUTORIA_PAGES_TO_SCRAPE_ENV_VAR_NAME;
import static org.automotive.scraper.autoria.AutoriaStringConstants.DEFAULT_COUNT_OF_PAGES_TO_SCRAPE;
import static org.automotive.utils.EnvVarUtils.getStringOrDefault;

import lombok.extern.slf4j.Slf4j;
import org.automotive.configuration.ApplicationConfiguration;
import org.automotive.job.ScrapingJob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Application {

    public static void main(String[] args) {
       log.info("Scraping {} pages...",
               getStringOrDefault(AUTORIA_PAGES_TO_SCRAPE_ENV_VAR_NAME, DEFAULT_COUNT_OF_PAGES_TO_SCRAPE));
        log.info("Started at: {}", System.nanoTime());
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        applicationContext.getBean(ScrapingJob.class).doJob();
        log.info("Ended at: {}", System.nanoTime());
    }

}
