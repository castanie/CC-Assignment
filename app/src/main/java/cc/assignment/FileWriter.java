package cc.assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileWriter {

    private FileWriter() {
    }

    public static void writeToFile(String fileContent, String path) {
        try {
            Files.writeString(Path.of(path), fileContent);
        } catch (IOException e) {
            ErrorLogger errorLogger = new ErrorLoggerAdapter(FileWriter.class);
            errorLogger.logError("Error when writing to file " + path + ": " + e.getMessage());
        }
    }
}
