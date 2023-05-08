package org.automotive.scraper;

import org.automotive.configuration.ApplicationConfiguration;
import org.automotive.scraper.autoria.AutoriaScraper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Disabled
/*This test doesn't make any sense. It was created just to test if selenium starts correctly*/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
class AutoriaScraperTest {

    @Autowired
    private AutoriaScraper autoriaScraper;

    @Test
    void testOpenSite() {
        autoriaScraper.openSite();
        autoriaScraper.applyFilters();
        String result = autoriaScraper.scrape();
        System.out.println(result);
    }
}