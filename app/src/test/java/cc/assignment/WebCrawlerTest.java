package cc.assignment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.nodes.Document;
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
            ExecutorService executor = Executors.newCachedThreadPool();
            this.webCrawler = new WebCrawler(executor, "https://example.com/", 1);
        }

        @Test
        @DisplayName("Basic call to getReport()")
        protected void testCrawl() {
            Document report = this.webCrawler.getHtmlReport();
            System.out.println(report.body());
        }
    }

    @Nested
    protected class Waldhalla {

        private WebCrawler webCrawler;

        @BeforeEach
        protected void beforeEach() {
            ExecutorService executor = Executors.newCachedThreadPool();
            this.webCrawler = new WebCrawler(executor, "https://waldhalla.com/", 1);
        }

        @Test
        @DisplayName("Basic call to getReport()")
        protected void testCrawl() {
            Document report = this.webCrawler.getHtmlReport();
            System.out.println(report.body());
        }
    }
}
