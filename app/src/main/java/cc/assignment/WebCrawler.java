package cc.assignment;

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
        Elements reportElements = this.document.select("a[href], h1, h2, h3, h3, h4, h5, h6").clone();
        condenseHtmlReportElements(reportElements);
        if (this.depth > 0) {
            embedAllLinkedHtmlDocuments(reportElements);
        }
        return reportElements;
    }

    private void condenseHtmlReportElements(Elements elements) {
        for (Element element : elements) {
            element.attr("data-text", element.text());
            element.empty();
        }
    }

    private void embedAllLinkedHtmlDocuments(Elements elements) {
        generateDocumentContainers(elements);
        try {
            this.executor.invokeAll(generateAsyncLoadingTasks(elements));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void generateDocumentContainers(Elements elements) {
        int index = 0;
        while (index < elements.size()) {
            if (elements.get(index).tagName().equals("a")) {
                Element container = new Element("div");
                elements.add(index + 1, container);
                ++index;
            }
            ++index;
        }
    }

    private List<Callable<Void>> generateAsyncLoadingTasks(Elements elements) {
        List<Callable<Void>> loadingTasks = new ArrayList<>();
        int index = 1;
        while (index < elements.size()) {
            if (elements.get(index).tagName().equals("div")) {
                Element a = elements.get(index - 1);
                Element div = elements.get(index);
                loadingTasks.add(() -> {
                    embedLinkedHtmlDocument(a, div);
                    return null;
                });
                ++index;
            }
            ++index;
        }
        return loadingTasks;
    }

    private void embedLinkedHtmlDocument(Element anchor, Element container) {
        String url = anchor.absUrl("href");
        URLValidator urlValidator = new URLValidator(url);
        if (!urlValidator.urlNotBroken()) {
            flagLinkAsBroken(anchor);
        }
        WebCrawler crawler = new WebCrawler(this.executor, url, this.depth - 1);
        container.appendChildren(crawler.getHtmlReportElements());
    }

    private void flagLinkAsBroken(Element anchor) {
        anchor.attr("data-text", anchor.attr("data-text") + " [BROKEN LINK OR REDIRECT]");
    }
}