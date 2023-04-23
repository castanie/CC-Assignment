package cc.assignment_1;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MarkdownConverterTest {
    @Nested
    protected class ExampleDepthOne {

        private MarkdownConverter mdConverter;

        @BeforeEach
        protected void beforeEach() {
            Document document = new Document("https://example.com/");
            document.body()
                    .append("""
                            <h1>Example Domain</h1>
                            <a href="https://www.iana.org/domains/example">More information...</a>
                            """);
            this.mdConverter = new MarkdownConverter(document);
        }

        @Test
        @DisplayName("Basic call to convertDocument()")
        protected void testConvertDocument() {
            System.out.println(mdConverter.convertDocument());
        }
    }

    @Nested
    protected class ExampleDepthTwo {

        private MarkdownConverter mdConverter;

        @BeforeEach
        protected void beforeEach() {
            Document document = new Document("https://example.com/");
            document.body()
                    .append("""
                            <h1>Example Domain</h1>
                            <div>
                                <a href="https://www.iana.org/domains/example">More information..</a>
                                <div>
                                    <a href="https://www.wikipedia.org">Uhmmm...</a>
                                </div>
                            </div>
                            """);
            this.mdConverter = new MarkdownConverter(document);
        }

        @Test
        @DisplayName("Basic call to convertDocument()")
        protected void testConvertDocument() {
            System.out.println(mdConverter.convertDocument());
        }
    }
}
