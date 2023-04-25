package cc.assignment_1;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

public class HeaderTranslator {
    Document doc;
    List<String> listOfHeaders;
    List<String> listOfTranslatedHeaders;
    int indexOfHeaderInList;

    public HeaderTranslator(Document doc) {
        this.doc = doc;
        listOfHeaders = new ArrayList<>();
        listOfTranslatedHeaders = new ArrayList<>();
    }

    public Document translateHeadersInDoc(String targetLanguage) {
        extractHeadersFromDoc();
        listOfTranslatedHeaders = Translator.getTranslator().translateListOFStrings(this.listOfHeaders, targetLanguage);
        setTranslatedHeadersInDoc();
        return this.doc;
    }

    protected void extractHeadersFromDoc() {
        this.doc.body().children().traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                if (node instanceof Element) {
                    extractHeadersFromNode(node);
                }
            }
        });
    }

    private void extractHeadersFromNode(Node node) {
        Element element = (Element) node;
        if (elementIsHeader(element)) {
            listOfHeaders.add(element.attr("data-text"));
        }
    }

    protected void setTranslatedHeadersInDoc() {
        this.indexOfHeaderInList = 0;
        doc.body().children().traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                if (node instanceof Element) {
                    setHeadersInNode((Element) node);
                }
            }
        });
    }

    private Element setHeadersInNode(Element element) {
        if (elementIsHeader(element) && element.attr("data-text").length() > 0) {
            element.attr("data-text", listOfTranslatedHeaders.get(indexOfHeaderInList));
            this.indexOfHeaderInList++;
        }
        return element;
    }

    private boolean elementIsHeader(Element element) {
        return element.tagName().equals("h1") || element.tagName().equals("h2") || element.tagName().equals("h3") || element.tagName().equals("h4") || element.tagName().equals("h5") || element.tagName().equals("h6");
    }
}
