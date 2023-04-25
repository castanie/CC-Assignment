package cc.assignment_1;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

public class HeaderTranslator {
    Document doc;
    List<String> headerList;
    List<String> headerListTranslated;
    int indexOfHeaderInList;

    public HeaderTranslator(Document doc) {
        this.doc = doc;
        headerList = new ArrayList<>();
        headerListTranslated = new ArrayList<>();
    }

    public Document translateHeadersInDoc(String targetLanguage) {
        extractHeadersFromDoc();
        headerListTranslated = Translator.getTranslator().translateListOFStrings(this.headerList, targetLanguage);
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
            headerList.add(element.attr("data-text"));
        }
    }

    private boolean elementIsHeader(Element element) {
        if (element.tagName().equals("h1") || element.tagName().equals("h2") || element.tagName().equals("h3") || element.tagName().equals("h4") || element.tagName().equals("h5") || element.tagName().equals("h6")) {
            return true;
        }
        return false;
    }

    protected void setTranslatedHeadersInDoc() {
        this.indexOfHeaderInList = 0;
        doc.body().children().traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                if (node instanceof Element) {
                    node = setHeadersInNode((Element) node);
                }
            }
        });
    }

    private Element setHeadersInNode(Element element) {
        //headerListTranslated.get()+
        if (elementIsHeader(element) && element.attr("data-text").length() > 0) {
            element.attr("data-text", headerListTranslated.get(indexOfHeaderInList));
            this.indexOfHeaderInList++;
        }

        return element;
    }
}
