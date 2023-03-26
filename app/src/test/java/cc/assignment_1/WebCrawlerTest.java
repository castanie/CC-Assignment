package cc.assignment_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class WebCrawlerTest {
    @Nested
    protected class Wikipedia {

        private WebCrawler webCrawler;

        @BeforeEach
        protected void beforeEach() {
            this.webCrawler = new WebCrawler("https://www.wikipedia.org/");
        }

        @Test
        @DisplayName("Basic call to crawl()")
        protected void testCrawl() {
            this.webCrawler.crawl();
        }
    }
}
