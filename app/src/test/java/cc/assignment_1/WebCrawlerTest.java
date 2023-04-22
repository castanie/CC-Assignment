package cc.assignment_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class WebCrawlerTest {
    @Nested
    protected class Example {

        private WebCrawler webCrawler;

        @BeforeEach
        protected void beforeEach() {
            this.webCrawler = new WebCrawler("https://example.com/", 2);
        }

        @Test
        @DisplayName("Basic call to crawlDocument()")
        protected void testCrawl() {
            System.out.println(MarkdownConverter.convertDocument(this.webCrawler.getSummary()));
        }
    }
}
