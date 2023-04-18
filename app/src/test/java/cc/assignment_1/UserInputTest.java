package cc.assignment_1;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UserInputTest {
    private UserInput userInput;

    @BeforeEach
    void setUp() {
        System.setIn(new ByteArrayInputStream("https://www.google.at\n3\nGerman".getBytes()));
        userInput = UserInput.getUserInput();
    }

    private void setUptTestReadUrlValid() {
        System.setIn(new ByteArrayInputStream("https://www.google.at".getBytes()));
        userInput.readUrl(new Scanner(System.in));
    }

    @Test
    void testReadUrlValid() {
        setUptTestReadUrlValid();
        assertEquals("https://www.google.at", userInput.getUrl());
    }

    private void setUpTestReadUrlInvalidThenValid() {
        System.setIn(new ByteArrayInputStream("https://at.google.www\nhttps://www.google.at".getBytes()));
        userInput.readUrl(new Scanner(System.in));
    }

    @Test
    void testReadUrlInvalidThenValid() {
        setUpTestReadUrlInvalidThenValid();
        assertEquals("https://www.google.at", userInput.getUrl());
    }

    private void setUpTestReadDepthValid() {
        System.setIn(new ByteArrayInputStream("3".getBytes()));
        userInput.readDepth(new Scanner(System.in));
    }

    @Test
    void testReadDepthValid() {
        setUpTestReadDepthValid();
        assertEquals(3, userInput.getDepth());
    }

    private void setUpTestReadDepthInvalidThenValid() {
        System.setIn(new ByteArrayInputStream("0\n3".getBytes()));
        userInput.readDepth(new Scanner(System.in));
    }

    @Test
    void testReadDepthInvalidThenValid() {
        setUpTestReadDepthInvalidThenValid();
        assertEquals(3, userInput.getDepth());
    }

    private void setUpTestReadLanguageValid() {
        System.setIn(new ByteArrayInputStream("English".getBytes()));
        userInput.readDepth(new Scanner(System.in));
    }

    @Test
    void testReadLanguageValid() {
        setUpTestReadLanguageValid();
        assertEquals("German", userInput.getTargetLanguage());
    }

    private void setUpTestReadLanguageInvalidThenValid() {
        userInput.setTargetLanguage(null);
        System.setIn(new ByteArrayInputStream("\nEnglish".getBytes()));
        userInput.readLanguage(new Scanner(System.in));
    }

    @Test
    void testReadLanguageInvalidThenValid() {
        setUpTestReadLanguageInvalidThenValid();
        assertEquals("English", userInput.getTargetLanguage());
    }

    @Test
    void testUrlIsValid() {
        assertTrue(userInput.urlIsValid("www.google.at"));
    }

    @Test
    void testUrlIsValidNull() {
        assertFalse(userInput.urlIsValid(null));
    }

    @Test
    void testUrlIsValidFalse() {
        assertFalse((userInput.urlIsValid("asdf")));
    }

    @Test
    void testUrlExistsTrue() {
        assertTrue(userInput.urlExists("https://www.google.at"));
    }

    @Test
    void testUrlExistsFalse() {
        assertFalse(userInput.urlExists("https://at.google.www"));
    }

    @Test
    void testAddProtocolIfMissing() {
        assertEquals("http://www.google.at", userInput.addProtocolIfMissing("www.google.at"));
    }

    @Test
    void testAddProtocolIfMissingNotMissing() {
        assertEquals("https://www.google.at", userInput.addProtocolIfMissing("https://www.google.at"));
    }

    @Test
    void testConnectToUrl() throws URISyntaxException, IOException {
        assertEquals(HttpURLConnection.HTTP_OK, userInput.connectToUrl("https://www.google.at"));
    }

    @Test
    void testConnectToUrlException() {
        assertThrows(Exception.class, () -> userInput.connectToUrl("https://at.google.www"));
        assertThrows(Exception.class, () -> userInput.connectToUrl("asdf"));
        assertThrows(Exception.class, () -> userInput.connectToUrl(""));
    }

    @Test
    void testDepthIsValid() {
        assertTrue(userInput.depthIsValid(4));
    }

    @Test
    void testDepthIsValidTooSmall() {
        assertFalse(userInput.depthIsValid(0));
    }

    @Test
    void testDepthIsValidTooBig() {
        assertFalse(userInput.depthIsValid(101));
    }

    @Test
    void testTargetLanguageIsValid() {
        assertTrue(userInput.targetLanguageIsValid("German"));
    }

    @Test
    void testTargetLanguageIsValidNull() {
        assertFalse(userInput.targetLanguageIsValid(null));
    }

    @Test
    void testTargetLanguageIsValidEmpty() {
        assertFalse(userInput.targetLanguageIsValid(""));
    }


}