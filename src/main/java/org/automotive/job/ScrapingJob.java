package org.automotive.job;

import lombok.extern.slf4j.Slf4j;
import org.automotive.bot.Bot;
import org.automotive.executor.ScrapingExecutor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ScrapingJob {

    private ScrapingExecutor scrapingExecutor;

    private Bot bot;

    private List<Long> chatIds;

    @PostConstruct
    private void init() {
        chatIds = new ArrayList<>();
    }

    public void doJob() {
        log.info("Staring scraping...");
        String scrapingResults = scrapingExecutor.execute();
        log.info("Scraping terminated");
        log.info("Sending scraping results");
        chatIds.forEach(chatId -> bot.sendMessage(scrapingResults, chatId));
        log.info("Scraping results sent successfully");
    }

}
