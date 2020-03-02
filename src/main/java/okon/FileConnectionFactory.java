package okon;

import okon.exception.AppException;

import java.nio.file.Path;

public class FileConnectionFactory {
    public static FileConnection makeConnection(String system, Path path) {
        if (system.equals("Windows")) {
            return new FileConnectionWindows(path);
        } else {
            throw new AppException("Błędnie podana nazwa systemu.");
        }
    }
}
