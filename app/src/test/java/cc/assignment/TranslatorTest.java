package cc.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {
    private Translator translator;

    @BeforeEach
    void setUp() {
        translator = new TranslatorAdapter();
    }

    List<String> getGermanStrings() {
        List<String> germanStrings = new ArrayList<>();
        germanStrings.add("Dies ist ein Test");
        germanStrings.add("Sonne");
        germanStrings.add("Kuchen");
        return germanStrings;
    }

    List<String> getSpanishStrings() {
        List<String> spanishStrings = new ArrayList<>();
        spanishStrings.add("Esta es una prueba");
        spanishStrings.add("Sol");
        spanishStrings.add("Pastel");
        return spanishStrings;
    }

    @Test
    void testTranslateListOfStrings() {
        assertEquals(getSpanishStrings(), translator.translateListOfStrings(getGermanStrings(), "Spanish"));
    }

    @Test
    void testLanguageExistsTrue() {
        assertTrue(translator.languageExists("German"));
        assertTrue(translator.languageExists("Norwegian"));
        assertTrue(translator.languageExists("French"));
    }

    @Test
    void testLanguageExistsFalse() {
        assertFalse(translator.languageExists("asdf"));
        assertFalse(translator.languageExists(""));
        assertFalse(translator.languageExists("Gernam"));
    }

    @Test
    void testGetLanguageCodeFromName() {
        assertEquals("de", translator.getLanguageCodeFromName("German"));
        assertEquals("fr", translator.getLanguageCodeFromName("French"));
        assertEquals("fi", translator.getLanguageCodeFromName("Finnish"));
    }
}