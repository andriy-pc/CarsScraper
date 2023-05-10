CREATE TABLE site
(
    id INT AUTO_INCREMENT,
    url VARCHAR(20) UNIQUE NOT NULL,
    CONSTRAINT PK_site PRIMARY KEY (id)
);

CREATE TABLE scraper_config
(
    id INT AUTO_INCREMENT,
    site_id INT,
    category VARCHAR(20) NOT NULL,
    make VARCHAR(20),
    model VARCHAR(20),
    minYear VARCHAR(5),
    maxYear VARCHAR(5),
    minPrice VARCHAR(20),
    maxPrice VARCHAR(20),
    default_config BIT NOT NULL DEFAULT 0,
    CONSTRAINT PK_scraper_config
        PRIMARY KEY (id),
    CONSTRAINT FK_site
        FOREIGN KEY (site_id)
            REFERENCES site (id)
                ON DELETE CASCADE
);

CREATE TABLE scraping_history
(
    id INT AUTO_INCREMENT,
    scraper_config_id INT,
    last_scraped_key VARCHAR (200) NOT NULL,
    CONSTRAINT PK_scraping_history
            PRIMARY KEY (id),
    CONSTRAINT FK_scraper_config
        FOREIGN KEY (scraper_config_id)
            REFERENCES scraper_config (id)
                ON DELETE CASCADE
);