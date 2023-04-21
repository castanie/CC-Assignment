package cc.assignment_1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import java.util.ArrayList;

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
        crawlDocument();

        //get doc;
        //parse file & extract headers and links
        //translate headers
        //store results in currentCrawlerResultData


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

    private ArrayList<String> crawlDocument() {
        ArrayList<String> foundURLs = new ArrayList<>();
        ArrayList<String> foundHeaders = new ArrayList<>();

        Document doc = getDocument();

        if (doc != null) {

            //select links
            for (Element link : doc.select("a[href]")) {
                String url = link.absUrl("href");
                //System.out.println(url);
                foundURLs.add(url);
            }

            //select headers
            for (Element header : doc.select("h1,h2,h3,h4,h5,h6")) {
                //System.out.println(header.text());
                foundHeaders.add(header.text());
            }
        }

        return foundURLs;
    }

    private Element traverseDoc() {
        return getDocument().traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                String tag = node.nodeName();
                if (tag.matches("h[1-6]")) {
                    System.out.println("HEADER: " + tag);
                    return;
                } else if (tag.matches("a")) {
                    System.out.println("LINK: " + tag);
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
