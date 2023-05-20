package cc.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorLoggerTest {
    private ErrorLogger errorLogger;
    String errorLog;

    @BeforeEach
    void setUp() {
        errorLogger = ErrorLogger.getErrorLogger();
        errorLogger.addNewErrorMessage("This is a test error message", "SomeClass", "SomeExceptionClass");
        errorLogger.addNewErrorMessage("Another error message", "AnotherClass", "AnotherExceptionClass");

        errorLog = """
                Error (SomeExceptionClass) in class SomeClass: This is a test error message
                Error (AnotherExceptionClass) in class AnotherClass: Another error message
                """;
    }

    @Test
    void getErrorLogTest() {
        assertEquals(errorLog, errorLogger.getErrorLog());
    }
}
