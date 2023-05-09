package cc.assignment;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.*;

public class URLValidatorTest {
    private URLValidator urlValidator;

    void setUp(String url) {
        urlValidator = new URLValidator(url);
    }

    @Test
    void testUrlExistsTrue() {
        setUp("www.google.at");
        assertTrue(urlValidator.urlIsValid());
    }

    @Test
    void testUrlExistsFalse() {
        setUp("https://at.google.www");
        assertFalse(urlValidator.urlIsValid());
    }

    @Test
    void testAddProtocolIfMissing() {
        setUp("www.google.at");
        assertEquals("https://www.google.at", urlValidator.addProtocolIfMissing());
    }

    @Test
    void testAddProtocolIfMissingNotMissing() {
        setUp("https://www.google.at");
        assertEquals("https://www.google.at", urlValidator.addProtocolIfMissing());
    }

    @Test
    void testConnectToUrl() throws URISyntaxException, IOException {
        setUp("https://www.google.at");
        assertEquals(HttpURLConnection.HTTP_OK, urlValidator.connectToUrl());
    }

    @Test
    void testConnectToUrlException() {
        setUp("https://at.google.www");
        assertThrows(Exception.class, () -> urlValidator.connectToUrl());

        setUp("asdf");
        assertThrows(Exception.class, () -> urlValidator.connectToUrl());

        setUp("");
        assertThrows(Exception.class, () -> urlValidator.connectToUrl());
    }
}
