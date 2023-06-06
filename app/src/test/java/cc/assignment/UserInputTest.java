package cc.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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
        userInput.readUrls(new Scanner(System.in));
    }

    @Test
    void testReadUrlValid() {
        setUptTestReadUrlValid();
        assertEquals("https://www.google.at", userInput.getUrls().get(0));
    }

    private void setUpTestReadUrlInvalidThenValid() {
        System.setIn(new ByteArrayInputStream("https://at.google.www\nhttps://www.google.at".getBytes()));
        userInput.readUrls(new Scanner(System.in));
    }

    @Test
    void testReadUrlInvalidThenValid() {
        setUpTestReadUrlInvalidThenValid();
        assertEquals("https://www.google.at", userInput.getUrls().get(0));
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