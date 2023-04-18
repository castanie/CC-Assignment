package cc.assignment_1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

public class WebCrawler {
    private final String url;
    private final int maxDepth;
    private final CrawlerResultData crawlerResult;

    public WebCrawler(String url, int maxDepth) {
        this.url = url;
        this.maxDepth = maxDepth;
        //this.document = this.getDocument();
        this.crawlerResult = new CrawlerResultData(url, false);
    }

    // Crawl:
    // Filter nodes (h* or a)
    // Dereference links (`append(crawl()`)

    public CrawlerResultData crawl() {
        crawlRecursively(this.crawlerResult, 0);
        return crawlerResult;
    }

    public void crawlRecursively(CrawlerResultData currentCrawlerResultData, int currentDepth) {

        //1) HANDLE CURRENT PAGE (TODO)
        //get doc;
        //parse file & extract headers and links & store them in currentCrawlerResultData
        

        //2) CHECK TERMINATION CONDITIONS
        if (currentDepth > maxDepth || currentCrawlerResultData.getChildren().size() == 0 || currentCrawlerResultData.isBroken()) {
            return;
        }

        //3) CRAWL RECURSIVELY
        for (String link : currentCrawlerResultData.getLinks()
        ) {
            CrawlerResultData child = new CrawlerResultData(link, new URLValidator(link).urlIsValid());
            currentCrawlerResultData.addChild(child);
            crawlRecursively(child, currentDepth + 1);
        }
    }

    private Element traverseDoc() {
        return getDocument().traverse(new NodeVisitor() {
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
            return Jsoup.connect(this.url).timeout(10000).get();
        } catch (Exception e) {
            return new Document(null);
        }
    }
}
