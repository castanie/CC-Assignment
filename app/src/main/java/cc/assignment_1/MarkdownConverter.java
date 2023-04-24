package cc.assignment_1;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

public class MarkdownConverter {
    private final Document document;
    private final StringBuilder builder;

    // --------------------------------

    public MarkdownConverter(Document document) {
        this.document = document;
        this.builder = new StringBuilder();
    }

    // --------------------------------

    public String convertDocument() {
        this.document.body().children().traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                convertNode(node, depth);
            }
        });
        return this.builder.toString();
    }

    private void convertNode(Node node, Integer depth) {
        if (node instanceof Element) {
            indentLine(depth);
            convertLine(node);
        }
    }

    private void indentLine(Integer depth) {
        this.builder.append(">".repeat(depth) + " ");
    }

    private void convertLine(Node node) {
        Element element = (Element) node;
        this.builder.append(
                switch (element.tagName()) {
                    case "a" -> "- " + element.attr("data-text") + " [" + node.absUrl("href") + "]\n";
                    case "h1" -> "# " + element.attr("data-text") + "\n";
                    case "h2" -> "## " + element.attr("data-text") + "\n";
                    case "h3" -> "### " + element.attr("data-text") + "\n";
                    case "h4" -> "#### " + element.attr("data-text") + "\n";
                    case "h5" -> "##### " + element.attr("data-text") + "\n";
                    case "h6" -> "###### " + element.attr("data-text") + "\n";
                    default -> "\n";
                });
    }
}
