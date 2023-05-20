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

    public String getErrorLog() {
        if (errorMessageArray.size() == 0) {
            return "Execution successful: No Errors occurred";
        }
        formatErrorLog();
        return builder.toString();
    }

    public void addNewErrorMessage(String errorMessage, String className, String exceptionClassName) {
        errorMessageArray.add(new Error(errorMessage, className, exceptionClassName));
    }

    private void formatErrorLog() {
        for (Error error : errorMessageArray
        ) {
            String formattedError = "Error (" + error.exceptionClassName() + ") in class " + error.className() + ": " + error.errorMessage() + "\n";
            this.builder.append(formattedError);
        }
    }
}
