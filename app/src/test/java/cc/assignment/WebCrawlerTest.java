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

        private PageCrawler pageCrawler;

        @BeforeEach
        protected void beforeEach() {
            ExecutorService executor = Executors.newCachedThreadPool();
            this.pageCrawler = new PageCrawler(executor, "https://example.com/", 1);
        }

        @Test
        @DisplayName("Basic call to getReport()")
        protected void testCrawl() {
            Document report = this.pageCrawler.getHtmlReport();
            System.out.println(report.body());
        }
    }

    @Nested
    protected class Waldhalla {

        private PageCrawler pageCrawler;

        @BeforeEach
        protected void beforeEach() {
            ExecutorService executor = Executors.newCachedThreadPool();
            this.pageCrawler = new PageCrawler(executor, "https://waldhalla.com/", 1);
        }

        @Test
        @DisplayName("Basic call to getReport()")
        protected void testCrawl() {
            Document report = this.pageCrawler.getHtmlReport();
            System.out.println(report.body());
        }
    }
}
