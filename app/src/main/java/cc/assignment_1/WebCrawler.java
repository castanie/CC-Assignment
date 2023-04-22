package cc.assignment_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
    private final String url;
    private final Integer depth;
    private final Document document;
    private final ExecutorService executor;

    // --------------------------------

    public WebCrawler(ExecutorService executor, String url, int depth) {
        this.url = url;
        this.depth = depth;
        this.document = this.loadDocument();
        this.executor = executor;
    }

    public WebCrawler(ExecutorService executor, String url) {
        this(executor, url, 2);
    }

    private Document loadDocument() {
        try {
            return Jsoup.connect(this.url).get();
        } catch (Exception e) {
            return new Document(this.url);
        }
    }

    // --------------------------------

    public Document getReport() {
        Document report = new Document(this.url);
        report.body().appendChildren(getReportElements());
        return report;
    }

    private Elements getReportElements() {
        Elements reportElements = this.document.select("a[href], h1, h2, h3, h3, h4, h5, h6");
        if (this.depth > 0) {
            embedLinkedDocuments(reportElements);
        }
        return reportElements;
    }

    private void embedLinkedDocuments(Elements elements) {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (Element element : elements) {
            if (element.tagName() == "a") {
                tasks.add(() -> {
                    embedLinkedDocument(element);
                    return null;
                });
            }
        }
        try {
            this.executor.invokeAll(tasks);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void embedLinkedDocument(Element element) {
        String url = element.absUrl("href");
        WebCrawler crawler = new WebCrawler(this.executor, url, this.depth - 1);
        element.appendChildren(crawler.getReportElements());
    }
}