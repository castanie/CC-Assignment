package cc.assignment;

import java.util.ArrayList;

public class ErrorLogger {
    private static final ErrorLogger errorLogger = new ErrorLogger();
    private final ArrayList<Error> errorMessageArray;
    private final StringBuilder builder;


    private ErrorLogger() {
        errorMessageArray = new ArrayList<>();
        this.builder = new StringBuilder();
    }

    public static ErrorLogger getErrorLogger() {
        return errorLogger;
    }

    public void addNewErrorMessage(String errorMessage, String className, String methodName) {
        errorMessageArray.add(new Error(errorMessage, className, methodName));
    }

    public void writeErrorLogToFile() {
        FileWriter.writeToFile(formatErrorsToString(), "error-log.md");
    }

    private String formatErrorsToString() {
        for (Error error : errorMessageArray
        ) {
            String formattedError = "Error in class " + error.className() + " in Method " + error.methodName() + ": " + error.errorMessage() + "\n";
            this.builder.append(formattedError);
        }
        return builder.toString();
    }
}
