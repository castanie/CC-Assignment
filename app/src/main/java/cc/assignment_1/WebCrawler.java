package cc.assignment_1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class WebCrawler {
    private final int maxDepth;
    private final CrawlerResultNode startingPageNode;
    private final ArrayList<String> visitedPages = new ArrayList<>();

    public WebCrawler(String url, int maxDepth) {
        this.maxDepth = maxDepth;
        this.startingPageNode = new CrawlerResultNode(url, false);
    }

    public CrawlerResultNode crawl() { //inits crawler recursion
        crawl(this.startingPageNode, 1);
        return startingPageNode;
    }

    public void crawl(CrawlerResultNode currentPage, int currentDepth) {
        visitedPages.add(currentPage.getUrl());

        if (currentPage.isBroken()) {
            return;
        }

        //just for testing purposes:
        System.out.println();
        System.out.println("______________________________________________________________________________");
        System.out.println("Searching Page: " + currentPage.getUrl() + " with current depth " + currentDepth);
        System.out.println("______________________________________________________________________________");
        System.out.println();

        parseContent(currentPage);

        if (currentDepth < maxDepth) {
            crawlLinksRecursively(currentPage, currentDepth);
        }
    }

    private void crawlLinksRecursively(CrawlerResultNode node, int currentDepth) {
        for (String link : node.getLinks()
        ) {
            if (!visitedPages.contains(link)) {
                CrawlerResultNode child = new CrawlerResultNode(link, !new URLValidator(link).urlIsValid());
                node.addChild(child);
                visitedPages.add(link);
                crawl(child, currentDepth + 1);
            }
        }
    }

    private CrawlerResultNode parseContent(CrawlerResultNode node) {
        Document doc = getDocument(node.getUrl());

        if (doc != null) {
            node.setHeaders(searchHeaders(doc));
            node.setLinks(searchLinks(doc));
        }

        return node;
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

    /*
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
    */

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).timeout(10000).get();
        } catch (Exception e) {
            return null;
        }
    }
}
