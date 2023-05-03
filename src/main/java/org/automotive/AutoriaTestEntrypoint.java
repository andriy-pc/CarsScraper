package org.automotive;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.automotive.scraper.Scraper;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
@AllArgsConstructor
public class AutoriaTestEntrypoint {

    private Scraper scraper;

    public static void main(String[] args) {
       new AutoriaTestEntrypoint().startScraping();
    }

    private void startScraping() {
        System.out.println("Scraping results: ");
        System.out.println(scraper.scrape());
    }

}
