package cc.assignment_1;

import java.util.ArrayList;

public class CrawlerResultNode {
    private final String url;
    private final boolean isBroken;
    private ArrayList<String> headers;
    private ArrayList<String> links;
    private final ArrayList<CrawlerResultNode> children;

    public CrawlerResultNode(String url, boolean isBroken) {
        this.url = url;
        this.isBroken = isBroken;
        this.headers = new ArrayList<>();
        this.links = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public boolean isBroken() {
        return isBroken;
    }

    protected ArrayList<String> getHeaders() {
        return headers;
    }

    protected void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    protected void setLinks(ArrayList<String> links) {
        this.links = links;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public ArrayList<CrawlerResultNode> getChildren() {
        return children;
    }

    protected void addChild(CrawlerResultNode child) {
        children.add(child);
    }
}
