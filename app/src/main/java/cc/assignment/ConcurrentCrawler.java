package cc.assignment;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentCrawler {
    Document htmlReport = new Document("");

    public Document generateHtmlReport(List<String> urls, int depth) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Callable<Void>> crawlingTasks = new ArrayList<>();
        for (String url : urls) {
            crawlingTasks.add(() -> {
                PageCrawler pageCrawler = new PageCrawler(
                        executor,
                        url,
                        depth);

                htmlReport.body().appendChild(pageCrawler.getHtmlReport().body());
                return null;
            });
        }

        try {
            executor.invokeAll(crawlingTasks);
        } catch (Exception e) {
            ErrorLogger errorLogger = new ErrorLoggerAdapter(PageCrawler.class);
            errorLogger.logError("Error invoking async tasks for embedding linked HTML documents: " + e.getMessage());
        }

        return htmlReport;
    }
}
