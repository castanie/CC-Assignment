package cc.assignment_1;

import java.util.Scanner;
import java.util.function.Function;

public class UserInput {
    private String url;
    private Integer depth;
    private String targetLanguage;
    private Scanner scanner;

    private UserInput() {
        this.scanner = new Scanner(System.in);
    }

    public String getUrl() {
        return url;
    }

    public Integer getDepth() {
        return depth;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public static UserInput promptUserInput() {
        UserInput userInput = new UserInput();
        userInput.promptUrl();
        userInput.promptDepth();
        userInput.promptLanguage();
        return userInput;
    }

    private void promptUrl() {
        this.url = promptString("Enter URL:", new Function<String, Boolean>() {
            @Override
            public Boolean apply(String t) {
                return urlIsValid(t);
            }
        });
    }

    private void promptDepth() {
        this.depth = promptInteger("Enter recursiondepth:", new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer t) {
                return depthIsValid(t);
            }
        });
    }

    private void promptLanguage() {
        this.url = promptString("Enter target language:", new Function<String, Boolean>() {
            @Override
            public Boolean apply(String t) {
                return targetLanguageIsValid(t);
            }
        });
    }

    private String promptString(String promptText, Function<String, Boolean> inputValidator) {
        System.out.println(promptText);
        String value = this.scanner.nextLine();
        if (!inputValidator.apply(value)) {
            printInvalidInputMessage();
            value = promptString(promptText, inputValidator);
        }
        return value;
    }

    private Integer promptInteger(String promptText, Function<Integer, Boolean> inputValidator) {
        System.out.println(promptText);
        Integer value = this.scanner.nextInt();
        this.scanner.nextLine();
        if (!inputValidator.apply(value)) {
            printInvalidInputMessage();
            value = promptInteger(promptText, inputValidator);
        }
        return value;
    }

    private void printInvalidInputMessage() {
        System.out.print("Invalid input, try again - ");
    }

    private boolean urlIsValid(String url) {
        return new URLValidator(url).urlIsValid();
    }

    private boolean depthIsValid(Integer depth) {
        return depth > 0 && depth < 10;
    }

    private boolean targetLanguageIsValid(String targetLanguage) {
        return targetLanguage != null && !targetLanguage.isEmpty()
                && Translator.getTranslator().supportsLanguage(targetLanguage);
    }
}
