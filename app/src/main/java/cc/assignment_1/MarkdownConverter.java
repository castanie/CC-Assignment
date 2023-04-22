package cc.assignment_1;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

public class MarkdownConverter {
    // private final Document document;

    // --------------------------------

    // public MarkdownConverter(Document document) {
    // this.document = document;
    // }

    // --------------------------------

    public static String convertDocument(Document document) {
        StringBuilder builder = new StringBuilder();
        document.body().children().traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                convertNode(builder, node, depth);
            }
        });
        return builder.toString();
    }

    private static void convertNode(StringBuilder builder, Node node, Integer depth) {
        if (node instanceof Element) {
            indentLine(builder, depth);
            convertLine(builder, node);
        }
    }

    private static void indentLine(StringBuilder builder, Integer depth) {
        builder.append(">".repeat(depth) + " ");
    }

    private static void convertLine(StringBuilder builder, Node node) {
        Element element = (Element) node;
        builder.append(
                switch (element.tagName()) {
                    case "a" -> "- ";// [" + node.attr("abs:href") + "] ";
                    case "h1" -> "# ";
                    case "h2" -> "## ";
                    case "h3" -> "### ";
                    case "h4" -> "#### ";
                    case "h5" -> "##### ";
                    case "h6" -> "###### ";
                    default -> "!!! " + element.tagName();
                }
                        + element.ownText() + "\n");
    }

}
