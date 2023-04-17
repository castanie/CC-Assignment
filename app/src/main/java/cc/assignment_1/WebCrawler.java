package cc.assignment_1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeVisitor;

public class WebCrawler {
    private final String url;
    private final Document document;

    public WebCrawler(String url) {
        this.url = url;
        this.document = this.getDocument();
    }

    // Crawl:
    // Filter nodes (h* or a)
    // Dereference links (`append(crawl()`)

    public Element crawl() {
        return this.document.traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                String tag = node.nodeName();
                if (tag.matches("h[1-6]")) {
                    return;
                } else if (tag.matches("a")) {

                } else {
                    node.remove();
                }
            }
        }); // select("h1, h2, h3, h4, h5, h6, a");
    }

    private Document getDocument() {
        try {
            return Jsoup.connect(this.url).get();
        } catch (Exception e) {
            return new Document(null);
        }
    }
}
