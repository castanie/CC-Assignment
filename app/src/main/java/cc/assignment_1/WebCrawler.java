package cc.assignment_1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
    private final String url;
    private final Integer depth;
    private final Document document;

    // --------------------------------

    public WebCrawler(String url, int depth) {
        this.url = url;
        this.depth = depth;
        this.document = this.loadDocument();
    }

    public WebCrawler(String url) {
        this(url, 2);
    }

    private Document loadDocument() {
        try {
            return Jsoup.connect(this.url).get();
        } catch (Exception e) {
            return new Document(this.url);
        }
    }

    // --------------------------------

    public Document getSummary() {
        Document summary = new Document(this.url);
        summary.body().appendChildren(crawlDocument());
        return summary;
    }

    private Elements crawlDocument() {
        Elements elements = getElements();
        if (this.depth > 0) {
            derefLinks(elements);
        }
        return elements;
    }

    private Elements getElements() {
        Elements elements = this.document.select("a[href], h1, h2, h3, h3, h4, h5, h6");
        for (Element element : elements) {
            // element.empty();
        }
        return elements;
    }

    private void derefLinks(Elements elements) {
        for (Element element : elements) {
            if (element.tagName() == "a") {
                derefLink(element);
            }
        }
    }

    private void derefLink(Element element) {
        String url = element.absUrl("href");
        WebCrawler crawler = new WebCrawler(url, this.depth - 1);
        element.appendChildren(crawler.crawlDocument());
    }
}