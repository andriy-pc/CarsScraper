package org.automotive;

import org.automotive.configuration.ApplicationConfiguration;
import org.automotive.job.ScrapingJob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        applicationContext.getBean(ScrapingJob.class).doJob();
    }

}
