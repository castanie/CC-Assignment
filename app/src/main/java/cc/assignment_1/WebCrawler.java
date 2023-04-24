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
        this.document = this.loadHtmlDocument();
        this.executor = executor;
    }

    public WebCrawler(ExecutorService executor, String url) {
        this(executor, url, 2);
    }

    private Document loadHtmlDocument() {
        try {
            return Jsoup.connect(this.url).get();
        } catch (Exception e) {
            return new Document(this.url);
        }
    }

    // --------------------------------

    public Document getHtmlReport() {
        Document report = new Document(this.url);
        report.body().appendChildren(getHtmlReportElements());
        return report;
    }

    private Elements getHtmlReportElements() {
        Elements reportElements = this.document.select("a[href], h1, h2, h3, h3, h4, h5, h6");
        condenseHtmlReportElements(reportElements);
        if (this.depth > 0) {
            embedLinkedHtmlDocuments(reportElements);
        }
        return reportElements;
    }

    private void condenseHtmlReportElements(Elements elements) {
        for (Element element : elements) {
            element.attr("data-text", element.text());
            element.empty();
        }
    }

    private void embedLinkedHtmlDocuments(Elements elements) {
        generateDivContainers(elements);
        try {
            this.executor.invokeAll(generateAsyncTasks(elements));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void generateDivContainers(Elements elements) {
        int index = 0;
        while (index < elements.size()) {
            if (elements.get(index).tagName().equals("a")) {
                generateSingleDivContainer(elements, index);
                ++index;
            }
            ++index;
        }
    }

    private void generateSingleDivContainer(Elements elements, Integer index) {
        Element container = new Element("div");
        container.attr("data-link", elements.get(index).attr("href"));
        elements.add(index + 1, container);
    }

    private List<Callable<Void>> generateAsyncTasks(Elements elements) {
        List<Callable<Void>> loadingTasks = new ArrayList<>();
        for (Element element : elements) {
            if (element.tagName().equals("div")) {
                loadingTasks.add(() -> {
                    embedLinkedHtmlDocument(element);
                    return null;
                });
            }
        }
        return loadingTasks;
    }

    private void embedLinkedHtmlDocument(Element element) {
        String url = element.absUrl("data-link");
        WebCrawler crawler = new WebCrawler(this.executor, url, this.depth - 1);
        element.appendChildren(crawler.getHtmlReportElements());
    }
}