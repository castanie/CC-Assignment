package cc.assignment_1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class UserInput {
    private static final UserInput userInput = new UserInput();
    private String url;
    private int depth;
    private String targetLanguage;

    private UserInput() {
        readUserInput();
    }

    public static UserInput getUserInput(){
        return userInput;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public void readUserInput(){
        Scanner scanner = new Scanner(System.in);

        readUrl(scanner);
        readDepth(scanner);
        readLanguage(scanner);
    }

    protected void readUrl(Scanner scanner){
        System.out.println("Enter URL: ");
        url = scanner.nextLine();
        while(!urlIsValid(url)){
            System.out.println("Invalid Input. Enter URL: ");
            url = scanner.nextLine();
        }
    }

    protected void readDepth(Scanner scanner){
        while(!depthIsValid(depth)){
            System.out.println("Enter depth: ");
            depth = scanner.nextInt();
            scanner.nextLine();
        }
    }

    protected void readLanguage(Scanner scanner){
        while(!targetLanguageIsValid(targetLanguage)){
            System.out.println(("Enter target language: "));
            targetLanguage = scanner.nextLine();
        }
    }

    protected boolean urlIsValid(String url){
        if(url==null){
            return false;
        }
        return (urlExists(url));
    }

    protected boolean urlExists(String url) {
        url = addProtocolIfMissing(url);
        try {
            if(connectToUrl(url) == HttpURLConnection.HTTP_OK){
                return true;
            }
            return false;
        } catch (URISyntaxException | IOException e) {
            return false;
        }
    }

    protected String addProtocolIfMissing(String url){

        if(!url.matches("^(https?)://.*$")){
            url = "http://" + url;
        }

        return url;
    }

    protected int connectToUrl(String url) throws URISyntaxException, IOException{
        new URL(url).toURI();
        URL urlObject = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("HEAD");

        return connection.getResponseCode();
    }

    protected boolean depthIsValid(int depth){
        if(depth<1 || depth >100){
            return false;
        }
        return true;
    }

    protected boolean targetLanguageIsValid(String targetLanguage){
        //TODO: only allow languages that actually exist
        if(targetLanguage == null || targetLanguage.length()==0){
            return false;
        }
        return true;
    }


}
