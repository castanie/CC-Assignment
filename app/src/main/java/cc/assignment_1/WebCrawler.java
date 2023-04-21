package cc.assignment_1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WebCrawler {
    private final String url;
    private final int maxDepth;
    private final CrawlerResultData crawlerResult;
    private final ArrayList<String> visitedPages = new ArrayList<>();

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
        crawlRecursively(this.crawlerResult, 1);
        return crawlerResult;
    }

    public void crawlRecursively(CrawlerResultData currentCrawlerResultData, int currentDepth) {
        visitedPages.add(currentCrawlerResultData.getUrl());

        System.out.println();
        System.out.println("______________________________________________________________________________");
        System.out.println("Searching Page: " + currentCrawlerResultData.getUrl() + " with current depth " + currentDepth);
        System.out.println("______________________________________________________________________________");
        System.out.println();


        //1) HANDLE CURRENT PAGE
        Document doc = getDocument(currentCrawlerResultData.getUrl());
        currentCrawlerResultData.setHeaders(searchHeaders(doc));
        currentCrawlerResultData.setLinks(searchLinks(doc));

        //TODO: Translate headers


        //2) CHECK TERMINATION CONDITIONS
        if (currentDepth > maxDepth || currentCrawlerResultData.getLinks().size() == 0 || currentCrawlerResultData.isBroken()) {
            return;
        }

        //3) CRAWL RECURSIVELY
        for (String link : currentCrawlerResultData.getLinks()
        ) {
            if (!visitedPages.contains(link)) {
                CrawlerResultData child = new CrawlerResultData(link, new URLValidator(link).urlIsValid());
                currentCrawlerResultData.addChild(child);
                crawlRecursively(child, currentDepth + 1);
            }
        }
    }

    private ArrayList<String> searchLinks(Document document) {
        ArrayList<String> foundURLs = new ArrayList<>();

        for (Element link : document.select("a[href]")) {
            String url = link.absUrl("href");
            System.out.println(url);
            foundURLs.add(url);
        }

        return foundURLs;
    }

    private ArrayList<String> searchHeaders(Document document) {
        ArrayList<String> foundHeaders = new ArrayList<>();

        for (Element header : document.select("h1,h2,h3,h4,h5,h6")) {
            System.out.println(header.text());
            foundHeaders.add(header.text());
        }

        return foundHeaders;
    }

    private Element traverseDoc() {
        return getDocument(url).traverse(new NodeVisitor() {
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

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).timeout(10000).get();
        } catch (Exception e) {
            return new Document(null);
        }
    }
}
