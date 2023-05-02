package cc.assignment;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTranslatorTest {

    @Nested
    protected class Example {
        protected HeaderTranslator headerTranslator;

        @BeforeEach
        protected void beforeEach() {
            Document document = new Document("https://some-url.com/");
            document.body()
                    .append("""
                            <h1 data-text="Hello"></h1>
                            <h2 data-text="I am a text"></h2>
                            <h3 data-text="Mountain"></h3>
                            <h4 data-text="Fish"></h4>
                            <h5 data-text="Water"></h5>
                            <h6 data-text="Fire"></h6>
                            <a href="https://www.another-url.com" data-text="The url text should not be translated..."></a>
                            """);
            this.headerTranslator = new HeaderTranslator(document);
        }

        protected String getTranslatedDocAsString() {
            Document document = new Document("https://some-url.com/");
            document.body()
                    .append("""
                            <h1 data-text="Hallo"></h1>
                            <h2 data-text="Ich bin ein Text"></h2>
                            <h3 data-text="Berg"></h3>
                            <h4 data-text="Fisch"></h4>
                            <h5 data-text="Wasser"></h5>
                            <h6 data-text="Feuer"></h6>
                            <a href="https://www.another-url.com" data-text="The url text should not be translated..."></a>
                            """);
            return String.valueOf(document);
        }

        @Test
        @DisplayName("Basic call to translate Document")
        protected void testTranslateDoc() {
            Document translatedDocument = headerTranslator.translateHeadersInDoc("German");
            assertEquals(getTranslatedDocAsString(), String.valueOf(translatedDocument));
            System.out.println(translatedDocument);
        }
    }
}