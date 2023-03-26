package cc.assignment_1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebCrawler {
    private final String url;
    private final Document document;

    public WebCrawler(String url) {
        this.url = url;
        this.document = this.getDocument();
    }

    public Elements crawl() {
        return this.document.select("h1, h2, h3, h4, h5, h6, a");
    }

    private Document getDocument() {
        try {
            return Jsoup.connect(this.url).get();
        } catch (Exception e) {
            return new Document(null);
        }
    }
}
