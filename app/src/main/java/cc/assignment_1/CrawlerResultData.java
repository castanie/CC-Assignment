package cc.assignment_1;

import java.util.ArrayList;

public class CrawlerResultData {
    private final String url;
    private final boolean isBroken;
    private ArrayList<String> headers;
    private ArrayList<String> links;
    private final ArrayList<CrawlerResultData> children;

    public CrawlerResultData(String url, boolean isBroken) {
        this.url = url;
        this.isBroken = isBroken;
        this.headers = new ArrayList<>();
        this.links = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public CrawlerResultData(String url, boolean isBroken, ArrayList<String> headers, ArrayList<String> links) {
        this.url = url;
        this.isBroken = isBroken;
        this.headers = headers;
        this.links = links;
        this.children = new ArrayList<>();
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

    public ArrayList<CrawlerResultData> getChildren() {
        return children;
    }

    protected void addChild(CrawlerResultData child) {
        children.add(child);
    }
}
