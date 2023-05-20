package cc.assignment;

import java.util.List;

public interface Translator {
    List<String> translateListOfStrings(List<String> input, String targetLanguage);

    boolean supportsLanguage(String language);

    String getLanguageCodeFromName(String languageName);
}
