package cc.assignment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErrorLoggerAdapter implements ErrorLogger {
    private static Logger logger;

    public ErrorLoggerAdapter(Class c) {
        logger = LogManager.getLogger(c);
    }

    @Override
    public void logError(String errorMessage) {
        logger.error(errorMessage);
    }
}
