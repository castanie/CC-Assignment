package cc.assignment;

import java.util.Scanner;

public class UserInput {
    private static final UserInput userInput = new UserInput();
    private String url;
    private int depth;
    private String targetLanguage;

    private UserInput() {
        readUserInput();
    }

    public static UserInput getUserInput() {
        return userInput;
    }

    public String getUrl() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public void readUserInput() {
        Scanner scanner = new Scanner(System.in);

        readUrl(scanner);
        readDepth(scanner);
        readLanguage(scanner);
    }

    protected void readUrl(Scanner scanner) {
        System.out.println("Enter URL: ");
        url = scanner.nextLine();
        while (!urlIsValid(url)) {
            System.out.println("Invalid Input. Enter URL: ");
            url = scanner.nextLine();
        }
    }

    protected void readDepth(Scanner scanner) {
        while (!depthIsValid(depth)) {
            System.out.println("Enter depth: ");
            depth = scanner.nextInt();
            scanner.nextLine();
        }
    }

    protected void readLanguage(Scanner scanner) {
        while (!targetLanguageIsValid(targetLanguage)) {
            System.out.println(("Enter target language: "));
            targetLanguage = scanner.nextLine();
        }
    }

    protected boolean urlIsValid(String url) {
        if (url == null) {
            return false;
        }
        URLValidator urlValidator = new URLValidator(url);
        return urlValidator.urlIsValid();
    }

    protected boolean depthIsValid(int depth) {
        if (depth < 1 || depth > 100) {
            return false;
        }
        return true;
    }

    protected boolean targetLanguageIsValid(String targetLanguage) {
        if (targetLanguage != null && targetLanguage.length() != 0 && Translator.getTranslator().languageExists(targetLanguage)) {
            return true;
        }
        return false;
    }
}
