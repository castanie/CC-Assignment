package cc.assignment_1;

import com.deepl.api.DeepLException;
import com.deepl.api.Language;
import com.deepl.api.TextResult;
import com.deepl.api.TranslatorOptions;

import java.util.ArrayList;

public class Translator extends com.deepl.api.Translator {
    private static final Translator translator = new Translator();
    private static final String translatorAuthKey = "24f3a0f9-3b07-56bc-2493-61d9824cb7d4:fx";

    private Translator() {
        super(translatorAuthKey, new TranslatorOptions());
    }

    public static Translator getTranslator() {
        return translator;
    }

    public String translateString(String input, String targetLanguage) {
        try {
            TextResult result = callTranslationMethod(input, targetLanguage);
            return result.getText();
        } catch (Exception e) {
            return "Translation failed";
        }
    }

    private TextResult callTranslationMethod(String text, String targetLanguage) throws DeepLException, InterruptedException {
        return translator.translateText(text, null, targetLanguage);
    }

    public boolean languageExists(String language) {
        try {
            if (listAvailableTargetLanguages().contains(language)) {
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public ArrayList<String> listAvailableTargetLanguages() throws DeepLException, InterruptedException {
        ArrayList<String> languages = new ArrayList<>();
        for (Language targetLanguage : translator.getTargetLanguages()) {
            languages.add(targetLanguage.getName());
        }
        return languages;
    }
}
