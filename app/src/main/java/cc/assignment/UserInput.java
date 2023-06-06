package cc.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInput {
    private static final UserInput userInput = new UserInput();
    private List<String> urls;
    private int depth;
    private String targetLanguage;

    private UserInput() {
        this.urls = new ArrayList<String>();
        readUserInput();
    }

    public static UserInput getUserInput() {
        return userInput;
    }

    public List<String> getUrls() {
        return this.urls;
    }

    public int getDepth() {
        return this.depth;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }

    public void readUserInput() {
        Scanner scanner = new Scanner(System.in);
        readUrls(scanner);
        readDepth(scanner);
        readLanguage(scanner);
    }

    protected void readUrls(Scanner scanner) {
        String url = "";
        do {
            System.out.println("Enter URL: ");
            url = scanner.nextLine();
            while (this.urls.isEmpty() && !urlIsValid(url)) {
                System.out.println("Invalid Input. Enter URL: ");
                url = scanner.nextLine();
            }
            if (!urlIsEmpty(url)) {
                this.urls.add(url);
            }
        } while (!urlIsEmpty(url));
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

    protected boolean urlIsEmpty(String url) {
        return url == null || "".equals(url);
    }

    protected boolean depthIsValid(int depth) {
        if (depth < 1 || depth > 100) {
            return false;
        }
        return true;
    }

    protected boolean targetLanguageIsValid(String targetLanguage) {
        Translator translator = new TranslatorAdapter();
        if (targetLanguage != null && targetLanguage.length() != 0 && translator.supportsLanguage(targetLanguage)) {
            return true;
        }
        return false;
    }
}
