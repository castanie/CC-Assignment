package cc.assignment;

import com.deepl.api.DeepLException;
import com.deepl.api.Language;
import com.deepl.api.TextResult;
import com.deepl.api.TranslatorOptions;

import java.util.ArrayList;
import java.util.List;

public class TranslatorAdapter implements Translator {
    private final String translatorAuthKey = "24f3a0f9-3b07-56bc-2493-61d9824cb7d4:fx";
    private final com.deepl.api.Translator deepLTranslator = new com.deepl.api.Translator(translatorAuthKey, new TranslatorOptions());

    @Override
    public List<String> translateListOfStrings(List<String> input, String targetLanguage) {
        List<TextResult> result;
        try {
            result = deepLTranslator.translateText(input, null, getLanguageCodeFromName(targetLanguage));
            return convertToStringList(result);
        } catch (DeepLException e) {
            //TODO: log message
        } catch (InterruptedException e) {
            //TODO: log message
        }
        return input;
    }

    private List<String> convertToStringList(List<TextResult> list) {
        List<String> stringList = new ArrayList<>();
        for (TextResult result : list) {
            stringList.add(result.getText());
        }
        return stringList;
    }

    @Override
    public boolean languageExists(String language) {
        try {
            if (listAvailableTargetLanguageNames().contains(language)) {
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public String getLanguageCodeFromName(String languageName) {
        for (Language language : getTargetLanguagesFromDeepL()) {
            if (language.getName().equals(languageName)) {
                return language.getCode();
            }
        }
        return "";
    }

    private ArrayList<String> listAvailableTargetLanguageNames() {
        ArrayList<String> languages = new ArrayList<>();
        for (Language targetLanguage : getTargetLanguagesFromDeepL()) {
            languages.add(targetLanguage.getName());
        }
        return languages;
    }

    private List<Language> getTargetLanguagesFromDeepL() {
        try {
            return deepLTranslator.getTargetLanguages();
        } catch (DeepLException e) {
            //TODO: log message
        } catch (InterruptedException e) {
            //TODO: log message
        }
        return new ArrayList<>();
    }
}
